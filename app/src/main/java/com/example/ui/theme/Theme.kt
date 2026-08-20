package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ElegantDarkPrimary,
    onPrimary = ElegantDarkOnPrimary,
    primaryContainer = ElegantDarkOnPrimary,
    onPrimaryContainer = ElegantDarkPrimary,
    secondary = PythonYellow,
    onSecondary = ElegantDarkOnPrimary,
    secondaryContainer = ElegantDarkSurfaceElevated,
    onSecondaryContainer = PythonYellow,
    tertiary = SuccessGreen,
    onTertiary = ElegantDarkOnPrimary,
    background = ElegantDarkBg,
    onBackground = ElegantDarkTextPrimary,
    surface = ElegantDarkSurface,
    onSurface = ElegantDarkTextPrimary,
    surfaceVariant = ElegantDarkSurfaceElevated,
    onSurfaceVariant = ElegantDarkTextSecondary,
    outline = ElegantDarkBorder,
    outlineVariant = ElegantDarkBorderMuted,
    error = ErrorRed,
    onError = ElegantDarkTextPrimary
)

private val LightColorScheme = darkColorScheme(
    primary = ElegantDarkPrimary,
    onPrimary = ElegantDarkOnPrimary,
    primaryContainer = ElegantDarkOnPrimary,
    onPrimaryContainer = ElegantDarkPrimary,
    secondary = PythonYellow,
    onSecondary = ElegantDarkOnPrimary,
    secondaryContainer = ElegantDarkSurfaceElevated,
    onSecondaryContainer = PythonYellow,
    tertiary = SuccessGreen,
    onTertiary = ElegantDarkOnPrimary,
    background = ElegantDarkBg,
    onBackground = ElegantDarkTextPrimary,
    surface = ElegantDarkSurface,
    onSurface = ElegantDarkTextPrimary,
    surfaceVariant = ElegantDarkSurfaceElevated,
    onSurfaceVariant = ElegantDarkTextSecondary,
    outline = ElegantDarkBorder,
    outlineVariant = ElegantDarkBorderMuted,
    error = ErrorRed,
    onError = ElegantDarkTextPrimary
)

@Composable
fun PythonMasteryTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
