package com.idigitalstudios.cineexplorerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CineRed,
    onPrimary = CineOnBackground,
    primaryContainer = CineRedDark,
    onPrimaryContainer = CineOnBackground,
    secondary = CineGold,
    onSecondary = CineBackground,
    secondaryContainer = CineSurfaceVariant,
    onSecondaryContainer = CineOnSurface,
    tertiary = CineGold,
    onTertiary = CineBackground,
    background = CineBackground,
    onBackground = CineOnBackground,
    surface = CineSurface,
    onSurface = CineOnSurface,
    surfaceVariant = CineSurfaceVariant,
    onSurfaceVariant = CineOnSurfaceVariant,
    error = CineRed,
    onError = CineOnBackground
)

private val LightColorScheme = lightColorScheme(
    primary = CineRedLight,
    onPrimary = CineOnBackground,
    primaryContainer = CineRedDark,
    onPrimaryContainer = CineOnBackground,
    secondary = CineGold,
    onSecondary = CineOnBackgroundLight,
    secondaryContainer = CineSurfaceVariantLight,
    onSecondaryContainer = CineOnSurfaceLight,
    tertiary = CineGold,
    onTertiary = CineOnBackgroundLight,
    background = CineBackgroundLight,
    onBackground = CineOnBackgroundLight,
    surface = CineSurfaceLight,
    onSurface = CineOnSurfaceLight,
    surfaceVariant = CineSurfaceVariantLight,
    onSurfaceVariant = CineOnSurfaceVariantLight,
    error = CineRedLight,
    onError = CineOnBackground
)

@Composable
fun CineExplorerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
