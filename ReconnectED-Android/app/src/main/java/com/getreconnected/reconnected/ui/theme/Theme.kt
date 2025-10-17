package com.getreconnected.reconnected.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Your strong accent green
val AppGreen = Color(0xFF10B981)

// Your dashboard gradient colors
val GradientStart = Color(0xFFD1FAE5)
val GradientEnd = Color(0xFFDBEAFE)

// UI card backgrounds and text colors
val CardSurface = Color(0xFFF4F4F4)
val OnPrimary = Color.White
val OnSurface = Color(0xFF262626)
val OnLightSurface = Color(0xFF595959)

private val lightColorScheme = lightColorScheme(
    primary = AppGreen,         // All major accents: top bar, icons, buttons
    secondary = AppGreen,
    tertiary = AppGreen,        // For StatCard/chart bars if used
    background = Color.White,   // fallback; actual dashboard uses gradient
    surface = CardSurface,      // For cards and panels
    onPrimary = OnPrimary,
    onSecondary = OnPrimary,
    onTertiary = OnPrimary,
    onBackground = OnSurface,
    onSurface = OnSurface,
)

private val darkColorScheme = darkColorScheme(
    primary = AppGreen,
    secondary = AppGreen,
    tertiary = AppGreen,
    background = Color(0xFF1A1A1A),
    surface = Color(0xFF232323),
    onPrimary = OnPrimary,
    onSecondary = OnPrimary,
    onTertiary = OnPrimary,
    onBackground = Color(0xFFF4F4F4),
    onSurface = Color(0xFFF4F4F4),
)

// Compose custom gradient colors into the theme
data class ReconnectEDColors(
    val gradientStart: Color,
    val gradientEnd: Color
)

val LocalReconnectEDColors = staticCompositionLocalOf {
    ReconnectEDColors(
        gradientStart = GradientStart,
        gradientEnd = GradientEnd
    )
}

@Composable
fun ReconnectEDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Always use your palette, never device!
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val customColors = ReconnectEDColors(GradientStart, GradientEnd)
    androidx.compose.runtime.CompositionLocalProvider(
        LocalReconnectEDColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
