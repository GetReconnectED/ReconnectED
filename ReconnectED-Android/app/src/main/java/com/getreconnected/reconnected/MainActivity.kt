package com.getreconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.ui.screens.MainScreen
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

/**
 * The main activity for the app.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        installSplashScreen()  // FIXME: I can't restore the splash screen :<
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReconnectEDTheme {
                MainScreen()
            }
        }
    }
}

