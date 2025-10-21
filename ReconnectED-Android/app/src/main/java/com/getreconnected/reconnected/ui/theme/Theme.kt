package com.getreconnected.reconnected.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Main color palette
val AppGreen = Color(0xFF008F46) // Primary sidebar/app bar/chart green
val SelectedGreen = Color(0xFF50C489) // Highlight green for selected sidebar + profile card
val GradientStart = Color(0xFFD1FAE5) // Gradient top color (mint green)
val GradientEnd = Color(0xFFDBEAFE) // Gradient bottom color (pale blue)
val CardSurface = Color(0xFFF4F4F4) // Card background
val DarkCardSurface = Color(0xFF232323)
val OnPrimary = Color.White
val OnSurface = Color(0xFF262626)

private val lightColorScheme =
    lightColorScheme(
        primary = AppGreen,
        onPrimary = OnPrimary,
        secondary = AppGreen,
        onSecondary = OnPrimary,
        tertiary = AppGreen,
        onTertiary = OnPrimary,
        background = Color.White,
        onBackground = OnSurface,
        surface = CardSurface,
        onSurface = OnSurface,
    )

private val darkColorScheme =
    darkColorScheme(
        primary = AppGreen,
        onPrimary = OnPrimary,
        secondary = AppGreen,
        onSecondary = OnPrimary,
        tertiary = AppGreen,
        onTertiary = OnPrimary,
        background = Color.White,
        onBackground = OnSurface,
        surface = CardSurface,
        onSurface = OnSurface,
    )

// Custom colors for gradient and sidebar selection
data class ReconnectEDColors(
    val gradientStart: Color,
    val gradientEnd: Color,
    val selectedGreen: Color,
)

val LocalReconnectEDColors =
    staticCompositionLocalOf {
        ReconnectEDColors(
            gradientStart = GradientStart,
            gradientEnd = GradientEnd,
            selectedGreen = SelectedGreen,
        )
    }

@Composable
@Suppress("ktlint:standard:function-naming")
fun ReconnectEDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val customColors = ReconnectEDColors(GradientStart, GradientEnd, SelectedGreen)
    androidx.compose.runtime.CompositionLocalProvider(
        LocalReconnectEDColors provides customColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
