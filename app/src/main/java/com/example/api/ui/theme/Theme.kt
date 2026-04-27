package com.example.api.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DoradoReal,
    secondary = DoradoOscuro,
    background = GrisCarbon,
    surface = GrisTarjeta,
    onPrimary = GrisCarbon,
    onBackground = TextoClaro,
    onSurface = TextoClaro
)

private val LightColorScheme = lightColorScheme(
    primary = DoradoReal,
    secondary = DoradoOscuro,
    background = BlancoFondo,
    surface = BlancoTarjeta,
    onPrimary = BlancoTarjeta,
    onBackground = TextoOscuro,
    onSurface = TextoOscuro
)

@Composable
fun ApiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}