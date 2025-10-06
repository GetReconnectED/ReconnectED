package com.getreconnected.reconnected.navigation

sealed class Screens (val route: String){
    object Splash : Screens("splash")
    object Login : Screens("login")
    object Dashboard : Screens("dashboard")

}