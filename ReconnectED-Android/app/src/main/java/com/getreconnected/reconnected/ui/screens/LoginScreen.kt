package com.getreconnected.reconnected.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.auth.GoogleAuth
import com.getreconnected.reconnected.core.dataManager.UserManager
import com.getreconnected.reconnected.core.models.Screens
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Suppress("ktlint:standard:function-naming")
fun LoginScreen(
    viewModel: UIRouteViewModel,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val firebaseAuth = FirebaseAuth.getInstance()
    val signInLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                Log.d("LoginScreen", "Result Code: ${result.resultCode}") // always 0
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    // Successfully signed in
                    Log.d("LoginScreen", "Google Sign-In successful. Trying to get user data...")
                    if (firebaseAuth.currentUser == null) {
                        Log.e("LoginScreen", "User is null")
                        Log.e("LoginScreen", "Getting user data failed.")
                    } else {
                        Log.d("LoginScreen", "User data retrieved successfully.")
                        viewModel.setActiveUser(firebaseAuth.currentUser?.displayName ?: "User")
                        Log.d("LoginScreen", "User ID: ${firebaseAuth.currentUser?.uid}")
                        Log.d("LoginScreen", "User email: ${firebaseAuth.currentUser?.email}")
                        Log.d("LoginScreen", "User name: ${firebaseAuth.currentUser?.displayName}")
                        coroutineScope.launch {
                            UserManager.login(firebaseAuth.currentUser!!)
                        }
                        Log.d("LoginScreen", "Navigating to Dashboard...")
                        navController.navigate(Screens.Dashboard.name) {
                            popUpTo(Screens.Login.name) { inclusive = true }
                        }
                    }
                } else {
                    // Sign in failed.
                    Log.e("LoginScreen", "Google Sign-In failed")
                }
            },
        )
    val googleAuth = GoogleAuth()

    if (firebaseAuth.currentUser != null) {
        LaunchedEffect(Unit) {
            UserManager.login(firebaseAuth.currentUser!!)
        }
        viewModel.setActiveUser(firebaseAuth.currentUser?.displayName ?: "User")
        // User is already signed in, navigate to the main screen
        navController.navigate(Screens.Dashboard.name)
    }

    var startAnimation by remember { mutableStateOf(false) }

    // Animate alpha (fade-in) and scale (zoom-in)
    val alphaAnim =
        animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(durationMillis = 1000), // 1 second
        )

    val scaleAnim =
        animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0.8f,
            animationSpec = tween(durationMillis = 1000),
        )

    // Trigger the animation shortly after the screen is composed
    LaunchedEffect(Unit) {
        delay(100) // A short delay to ensure a smooth start
        startAnimation = true
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFF008F46))
                .padding(horizontal = 40.dp)
                .alpha(alphaAnim.value) // Apply the fade-in to the whole screen
                .scale(scaleAnim.value),
        // Apply the scale to the whole screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.recologo_ca),
            contentDescription = "App Logo",
            modifier = Modifier.width(338.dp).height(331.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.google_signin_button),
            contentDescription = "Sign in with Google",
            modifier =
                Modifier.width(210.dp).height(49.dp).clip(RoundedCornerShape(20.dp)).clickable {
                    val signInIntent: Intent? = googleAuth.showLogin()
                    signInLauncher.launch(signInIntent)
                },
        )
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun LoginScreenPreview() {
    val viewModel: UIRouteViewModel = viewModel()
    ReconnectEDTheme { LoginScreen(viewModel = viewModel, navController = rememberNavController()) }
}
