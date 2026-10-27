package com.lifeos.app.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = LifeOSPrimaryAccent,
    onPrimary = LifeOSBackground,
    primaryContainer = LifeOSSecondarySurface,
    onPrimaryContainer = LifeOSPrimaryAccent,
    secondary = LifeOSSecondaryAccent,
    onSecondary = LifeOSBackground,
    background = LifeOSBackground,
    onBackground = LifeOSTextPrimary,
    surface = LifeOSCardBackground,
    onSurface = LifeOSTextPrimary,
    surfaceVariant = LifeOSSecondarySurface,
    onSurfaceVariant = LifeOSTextSecondary,
    outline = LifeOSBorder,
    error = LifeOSError,
    onError = LifeOSBackground
)

@Composable
fun LifeOSTheme(
    darkTheme: Boolean = true, // LifeOS is a dedicated dark interface by design
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = LifeOSBackground.toArgb()
                window.navigationBarColor = LifeOSBackground.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LifeOSTypography,
        shapes = LifeOSShapes,
        content = content
    )
}
