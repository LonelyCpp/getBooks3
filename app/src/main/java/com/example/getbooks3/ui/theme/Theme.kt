package com.example.getbooks3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
        primary = Color(0xFFf4989c),
        primaryVariant = Color(0xFFEB4750),
        secondary = Color(0xff2e2e3a)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFf4989c),
    primaryVariant = Color(0xFFEB4750),
    secondary = Color(0xff2e2e3a)
)

@Composable
fun GetBooks3Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}