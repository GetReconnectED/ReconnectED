package com.getreconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.data.ReconnectedViewModelFactory
import com.getreconnected.reconnected.navigation.ReconnectedViewModel
import com.getreconnected.reconnected.screens.MainScreen
import com.getreconnected.reconnected.ui.theme.ReconnectED_ATheme

class HomeActivity : ComponentActivity() {
    private val viewModel: ReconnectedViewModel by viewModels {
        ReconnectedViewModelFactory((application as ReconnectedApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            ReconnectED_ATheme {
                MainScreen(Modifier, viewModel)
            }
        }
    }
}