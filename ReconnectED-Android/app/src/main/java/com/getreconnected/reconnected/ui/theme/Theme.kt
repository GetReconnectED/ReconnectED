package com.getreconnected.reconnected.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val lightColorScheme =
    lightColorScheme(
        primary = LightThemeColors.primary,
        secondary = LightThemeColors.secondary,
        tertiary = LightThemeColors.tertiary,
        background = LightThemeColors.backgroundBase,
        /* Other default colors to override
        surface = Color(0xFFFFFBFE),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = Color(0xFF1C1B1F),
        onSurface = Color(0xFF1C1B1F),
         */
    )

private val darkColorScheme =
    darkColorScheme(
        primary = DarkThemeColors.primary,
        secondary = DarkThemeColors.secondary,
        tertiary = DarkThemeColors.tertiary,
        background = DarkThemeColors.backgroundBase,
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
