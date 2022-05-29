package com.death.datastoreplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.death.datastoreplugin.serializer.userDataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.add).setOnClickListener {
            lifecycleScope.launch {
                userDataStore.updateData {
                    it.toBuilder()
                        .setIsLoggedIn(!it.isLoggedIn ?: false)
                        .setUsername("Random")
                        .build()
                }
            }
        }
    }
}