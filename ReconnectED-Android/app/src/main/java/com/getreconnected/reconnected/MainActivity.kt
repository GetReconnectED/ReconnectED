package com.getreconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.navigation.AppNavigation
import com.getreconnected.reconnected.ui.theme.ReconnectED_ATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            ReconnectED_ATheme {
                AppNavigation()
            }
        }
    }
}