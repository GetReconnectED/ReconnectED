package com.getreconnected.reconnected.core.models

/**
 * The available menus for the app.
 */
enum class Menus(
    val title: String,
) {
    Dashboard("Dashboard"),
    ScreenTimeTracker("Screen Time Tracker"),
    ScreenTimeLimit("Screen Time Limit"),
    Calendar("Calendar"),
    Assistant("AI Assistant"),
    Unknown(""),
}

/**
 * Converts a route string to a menu.
 *
 * @param route The route string to convert.
 */
fun getMenuRoute(route: String): Menus = Menus.entries.find { it.title == route } ?: Menus.Unknown
