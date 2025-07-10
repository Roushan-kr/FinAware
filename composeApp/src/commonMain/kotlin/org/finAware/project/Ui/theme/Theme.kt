package org.finAware.project.Ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkColor,
    secondary = SecondaryDarkColor,
    background = BackgroundDarkColor,
    surface = SurfaceDarkColor,
    onPrimary = OnPrimaryDarkColor,
    onSecondary = OnSecondaryDarkColor,
    onBackground = OnBackgroundDarkColor,
    onSurface = OnSurfaceDarkColor
)

@Composable
fun FinAwareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
