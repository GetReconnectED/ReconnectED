package com.getreconnected.reconnected.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.core.DatabaseManager
import com.getreconnected.reconnected.ui.AppNavigation
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

/**
 * The main activity for the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "Starting main activity")
        installSplashScreen() // This must be called BEFORE super.onCreate()

        super.onCreate(savedInstanceState)

        // Get database instance
        Log.d("MainActivity", "Getting database instance")
        val database = DatabaseManager.getDatabase(this)

        Log.d("MainActivity", "Setting content")
        setContent { ReconnectEDTheme { AppNavigation(database) } }
    }
}
