package com.getreconnected.reconnected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import com.getreconnected.reconnected.data.ReconnectedViewModelFactory
import com.getreconnected.reconnected.ui.navigation.ReconnectedViewModel
import com.getreconnected.reconnected.ui.screens.MainScreen
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ReconnectedViewModel by viewModels {
        ReconnectedViewModelFactory((application as ReconnectedApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // This must be called BEFORE super.onCreate()
        super.onCreate(savedInstanceState)
        setContent { ReconnectEDTheme { MainScreen(Modifier, viewModel) } }
    }
}
