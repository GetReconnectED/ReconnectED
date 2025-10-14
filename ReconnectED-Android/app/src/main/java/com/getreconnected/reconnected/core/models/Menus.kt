package com.getreconnected.reconnected.core.models

/**
 * The available menus for the app.
 *
 * @property title The title of the menu.
 */
enum class Menus(
    val title: String,
) {
    Dashboard("Dashboard"),
    ScreenTimeTracker("Screen Time Tracker"),
    ScreenTimeLimit("Screen Time Limit"),
    Calendar("Calendar"),
    Assistant("AI Assistant"),
}

/**
 * Converts a route string to a menu.
 *
 * @param route The route string to convert.
 * @return The corresponding menu.
 */
fun getMenuRoute(route: String): Menus =
    Menus.entries.find { it.title == route } ?: throw IllegalArgumentException("Invalid menu route: $route")
