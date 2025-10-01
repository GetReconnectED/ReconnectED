package com.getreconnected.reconnected.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.getreconnected.reconnected.BuildConfig
import com.getreconnected.reconnected.core.APPLICATION_NAME
import com.getreconnected.reconnected.ui.theme.ReconnectEdTheme


/**
 * Display a greeting message.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! Welcome to $APPLICATION_NAME v${BuildConfig.VERSION_NAME}",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReconnectEdTheme {
        Greeting("User")
    }
}