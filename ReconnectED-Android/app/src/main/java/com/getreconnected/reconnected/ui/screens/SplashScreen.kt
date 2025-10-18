package com.getreconnected.reconnected.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.ui.composables.elements.StatCard
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import kotlinx.coroutines.delay

@Composable
@Suppress("ktlint:standard:function-naming")
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000L)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
            color = MaterialTheme.colorScheme.primary
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.recologo_ca),
            contentDescription = "Companion App Logo",
            // This modifier makes the logo responsive
            modifier =
                Modifier
                    .fillMaxSize(
                        0.75f,
                    ) // The logo will take up 75% of the screen's width
                    .padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun SplashScreenPreview() {
    ReconnectEDTheme {
        SplashScreen {}
    }
}
