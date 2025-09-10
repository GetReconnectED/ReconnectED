package com.reconnected.reconnected.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.reconnected.reconnected.R
import com.reconnected.reconnected.ui.theme.DashboardStatCardTextColor
import kotlinx.coroutines.delay

/**
 * The splash screen of the app.
 *
 * @param onTimeout The callback to be invoked when the splash screen times out.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun SplashScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        delay(2000L)
        onTimeout()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DashboardStatCardTextColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.recologo_ca),
            contentDescription = "Companion App Logo",
            // This modifier makes the logo responsive
            modifier = modifier
                .fillMaxWidth(fraction = 0.75f) // The logo will take up 75% of the screen's width
                .padding(16.dp)
        )
    }
}