package com.getreconnected.reconnected.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

/**
 * The main activity for the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // This must be called BEFORE super.onCreate()

        super.onCreate(savedInstanceState)
        setContent { ReconnectEDTheme {} }
    }
}
