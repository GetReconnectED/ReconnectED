package com.getreconnected.reconnected.navigation
// In navigation/AppNavigation.kt
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.screens.LoginScreen // You will create this file next
import com.getreconnected.reconnected.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screens.Login.route) {
                    // Clears the splash screen from back stack
                    popUpTo(Screens.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screens.Login.route) {
            LoginScreen(navController = navController) // We'll create this screen soon
        }

//        composable(Screens.Dashboard.route){
//            MainScreen()
//        }
    }
}
