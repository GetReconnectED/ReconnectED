package com.reconnected.reconnected.navigation

sealed class Screens(val route: String) {
    data object SplashScreen : Screens("splash_screen")
    data object LoginScreen : Screens("login_screen")
    data object DashboardScreen : Screens("dashboard_screen")
}
