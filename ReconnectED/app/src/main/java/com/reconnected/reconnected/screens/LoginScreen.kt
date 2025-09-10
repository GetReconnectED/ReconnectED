package com.reconnected.reconnected.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.reconnected.reconnected.R
import com.reconnected.reconnected.navigation.Screens
import com.reconnected.reconnected.ui.theme.DashboardStatCardTextColor
import com.reconnected.reconnected.ui.theme.DefaultFadeSpeed
import com.reconnected.reconnected.ui.theme.ReconnectEDTheme
import kotlinx.coroutines.delay

/**
 * The login screen of the app.
 *
 * @param navController The navigation controller to navigate between screens.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun LoginScreen(
    navController: NavController, modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animate alpha (fade-in) and scale (zoom-in)
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = DefaultFadeSpeed)
    )

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(durationMillis = DefaultFadeSpeed)
    )

    // Trigger the animation shortly after the screen is composed
    LaunchedEffect(Unit) {
        delay(100) // A short delay to ensure a smooth start
        startAnimation = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DashboardStatCardTextColor)
            .padding(horizontal = 40.dp)
            .alpha(alphaAnim.value) // Apply the fade-in to the whole screen
            .scale(scaleAnim.value), // Apply the scale to the whole screen
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.recologo_ca),
            contentDescription = "App Logo",
            modifier = modifier
                .width(338.dp)
                .height(331.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.google_signin_button),
            contentDescription = "Sign in with Google",
            modifier = modifier
                .width(210.dp)
                .height(49.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    navController.navigate(Screens.DashboardScreen.route)
                })
        Spacer(modifier = modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ReconnectEDTheme {
        LoginScreen(rememberNavController())
    }
}
