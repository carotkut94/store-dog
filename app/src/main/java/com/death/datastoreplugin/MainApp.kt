package com.death.datastoreplugin

import android.app.Application
import com.death.datastoreplugin.plugin.StoreDogPlugin
import com.death.datastoreplugin.serializer.userDataStore
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        val client = AndroidFlipperClient.getInstance(this)
        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(StoreDogPlugin(mapOf("User Store" to this.userDataStore)))
        client.start()
    }
}