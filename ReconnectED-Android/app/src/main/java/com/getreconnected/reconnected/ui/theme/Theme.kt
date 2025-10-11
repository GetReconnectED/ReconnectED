package com.getreconnected.reconnected.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightColorScheme =
    lightColorScheme(
        primary = Color(0xFF10B981), // buttons and links
        secondary = Color(0xFF5BB56E), // complement the primary color, including toggles
        tertiary = Color(0xFF008F46), // accent elements or less prominent controls
        background = Color(0xFF1A1A1A), // background color for screens, surfaces, and cards
        surface = Color(0xFF262626), // surfaces like cards, sheets, and dialogs
        onPrimary = Color(0xFFEFF1F5), // text and icons placed on top of the primary color
        onSecondary = Color(0xFFE6E9EF), // used on the secondary color
        onTertiary = Color(0xFFDCE0E8), // used on the tertiary color
        onBackground = Color.Red, // text and icons placed on the background color
        onSurface = Color.Blue, // text and icons on surfaces
    )

private val darkColorScheme =
    darkColorScheme(
        primary = Color(0xFF10B981), // buttons and links
        secondary = Color(0xFF5BB56E), // complement the primary color, including toggles
        tertiary = Color(0xFF008F46), // accent elements or less prominent controls
        background = Color(0xFF1A1A1A), // background color for screens, surfaces, and cards
        surface = Color(0xFF262626), // surfaces like cards, sheets, and dialogs
        onPrimary = Color(0xFFEFF1F5), // text and icons placed on top of the primary color
        onSecondary = Color(0xFFE6E9EF), // used on the secondary color
        onTertiary = Color(0xFFDCE0E8), // used on the tertiary color
        onBackground = Color.Red, // text and icons placed on the background color
        onSurface = Color.Blue, // text and icons on surfaces
    )

/**
 * The main theme for the app.
 *
 * @param darkTheme Whether the app is in dark theme mode.
 * @param dynamicColor Whether the app should use dynamic colors.
 * @param content The content of the app.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ReconnectEDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            dynamicColor -> {
                if (darkTheme) {
                    dynamicDarkColorScheme(LocalContext.current)
                } else {
                    dynamicLightColorScheme(LocalContext.current)
                }
            }
            darkTheme -> darkColorScheme
            else -> lightColorScheme
        }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
