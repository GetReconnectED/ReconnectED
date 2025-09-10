package com.reconnected.reconnected.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reconnected.reconnected.screens.DashboardScreen
import com.reconnected.reconnected.screens.LoginScreen
import com.reconnected.reconnected.screens.SplashScreen

/**
 * The navigation graph for the app.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screens.LoginScreen.route) {
                    // Clears the splash screen from back stack
                    popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
            })
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.DashboardScreen.route) {
            DashboardScreen()
        }
    }
}
