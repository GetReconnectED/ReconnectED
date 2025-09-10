package com.reconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.reconnected.reconnected.navigation.AppNavigation
import com.reconnected.reconnected.ui.theme.ReconnectEDTheme

/**
 * The main activity of the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            ReconnectEDTheme {
                AppNavigation()
            }
        }
    }
}