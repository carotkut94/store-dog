package com.death.datastoreplugin.plugin

import androidx.datastore.core.DataStore
import com.facebook.flipper.core.FlipperConnection
import com.facebook.flipper.core.FlipperObject
import com.facebook.flipper.core.FlipperPlugin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class StoreDogPlugin(private val dataStores: Map<String, DataStore<*>>) : FlipperPlugin {
    private var connection: FlipperConnection? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getId(): String = "store-dog"
    override fun onConnect(connection: FlipperConnection?) {
        this.connection = connection
        coroutineScope.launch {
            dataStores.forEach { (key, store) ->
                store.data.collect { data ->
                    newRow(
                        FlipperObject.Builder()
                            .put("id", key)
                            .put("store", key)
                            .put("payload", data.toString()).build()
                    )
                }
            }
        }
    }

    override fun onDisconnect() {
        connection = null
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun runInBackground(): Boolean = false

    private fun newRow(row: FlipperObject) {
        connection?.send("newRow", row)
    }
}