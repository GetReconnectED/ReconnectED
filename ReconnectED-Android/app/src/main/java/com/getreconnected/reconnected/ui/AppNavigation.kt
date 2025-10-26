package com.getreconnected.reconnected.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.core.models.Screens
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.screens.LoginScreen
import com.getreconnected.reconnected.ui.screens.MainScreen
import com.getreconnected.reconnected.ui.screens.SplashScreen

@Composable
@Suppress("ktlint:standard:function-naming")
fun AppNavigation() {
    val viewModel: UIRouteViewModel = viewModel() // Initialize the view model for UI routes
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.name) {
        composable(Screens.Splash.name) {
            SplashScreen(onTimeout = {
                navController.navigate(Screens.Login.name) {
                    // Clears the splash screen from back stack
                    popUpTo(Screens.Splash.name) { inclusive = true }
                }
            })
        }
        composable(Screens.Login.name) {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screens.Dashboard.name) {
            MainScreen(viewModel)
        }
    }
}
