package com.getreconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.ui.navigation.AppNavigation
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme


/** The main activity for the app. */
class LoginActivity : ComponentActivity() {
    /** Called when the activity is starting. */
    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent { ReconnectEDTheme { AppNavigation() } }
    }
}
