package com.getreconnected.reconnected.core.models

/**
 * The available screens for the app.
 */
enum class Screens {
    Splash,
    Login,
    Dashboard,
}

/**
 * Converts a screen name to a screen.
 *
 * @param name The name of the screen string to convert.
 */
fun getScreenName(name: String): Screens =
    Screens.entries.find { it.name == name } ?: throw IllegalArgumentException("Invalid route: $name")
