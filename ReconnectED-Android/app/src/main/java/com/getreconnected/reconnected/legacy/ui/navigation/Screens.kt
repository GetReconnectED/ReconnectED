package com.getreconnected.reconnected.legacy.ui.navigation

sealed class Screens(
    val route: String,
) {
    object Splash : Screens("splash")

    object Login : Screens("login")

    object Dashboard : Screens("dashboard")
}
