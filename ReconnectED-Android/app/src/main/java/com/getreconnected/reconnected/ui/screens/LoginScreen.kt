package com.getreconnected.reconnected.ui.screens

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.LoginActivity
import com.getreconnected.reconnected.MainActivity
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.auth.GoogleAuth
import com.getreconnected.reconnected.ui.navigation.Screens
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var startAnimation by remember { mutableStateOf(false) }

    // Animate alpha (fade-in) and scale (zoom-in)
    val alphaAnim =
            animateFloatAsState(
                    targetValue = if (startAnimation) 1f else 0f,
                    animationSpec = tween(durationMillis = 1000) // 1 second
            )

    val scaleAnim =
            animateFloatAsState(
                    targetValue = if (startAnimation) 1f else 0.8f,
                    animationSpec = tween(durationMillis = 1000)
            )

    // Trigger the animation shortly after the screen is composed
    LaunchedEffect(Unit) {
        delay(100) // A short delay to ensure a smooth start
        startAnimation = true
    }

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(Color(0xFF008F46))
                            .padding(horizontal = 40.dp)
                            .alpha(alphaAnim.value) // Apply the fade-in to the whole screen
                            .scale(scaleAnim.value), // Apply the scale to the whole screen
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Image(
                painter = painterResource(id = R.drawable.recologo_ca),
                contentDescription = "App Logo",
                modifier = Modifier.width(338.dp).height(331.dp)
        )
        Image(
                painter = painterResource(id = R.drawable.google_signin_button),
                contentDescription = "Sign in with Google",
                modifier =
                        Modifier.width(210.dp)
                                .height(49.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    /*val loginIntent = Intent(context, MainActivity::class.java)
                                    loginIntent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                                    FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(loginIntent)*/
                                    val googleAuth = GoogleAuth()
                                    googleAuth.signIn()
                                    navController.navigate(Screens.Dashboard.route) {
                                        // Clears the splash screen from back stack
                                        popUpTo(Screens.Splash.route) { inclusive = true }
                                    }
                                }
        )
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ReconnectEDTheme { LoginScreen(rememberNavController()) }
}
