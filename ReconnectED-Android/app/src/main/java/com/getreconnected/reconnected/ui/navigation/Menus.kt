package com.getreconnected.reconnected.ui.navigation

sealed class Menus(val route: String) {
    object Dashboard : Menus("dashboard")
    object ScreenTimeTracker : Menus("screen_time_tracker")
    object ScreenTimeLimit : Menus("screen_time_limit")
    object Calendar : Menus("calendar")
    object Assistant : Menus("assistant")
}
