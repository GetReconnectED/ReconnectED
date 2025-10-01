package com.getreconnected.reconnected

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.getreconnected.reconnected.ui.composables.Greeting
import com.getreconnected.reconnected.ui.theme.ReconnectEdTheme

/**
 * The main activity for the app.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReconnectEdTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = Build.USER,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

