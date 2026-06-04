package com.idigitalstudios.cineexplorerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Series", Icons.Filled.Home)
    data object Favorites : Screen("favorites", "Favoritos", Icons.Filled.Favorite)
    data object Device : Screen("device", "Dispositivo", Icons.Filled.PhoneAndroid)
}

val bottomNavScreens = listOf(Screen.Home, Screen.Favorites, Screen.Device)
