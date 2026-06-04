package com.idigitalstudios.cineexplorerapp.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.idigitalstudios.cineexplorerapp.presentation.detail.DetailScreen
import com.idigitalstudios.cineexplorerapp.presentation.device.DeviceScreen
import com.idigitalstudios.cineexplorerapp.presentation.favorites.FavoritesScreen
import com.idigitalstudios.cineexplorerapp.presentation.home.HomeScreen
import com.idigitalstudios.cineexplorerapp.presentation.splash.SplashScreen

@Composable
fun CineExplorerNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = bottomNavScreens.any { it.route == currentRoute }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("splash") {
                SplashScreen(onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo("splash") { inclusive = true }
                    }
                })
            }
            composable(Screen.Home.route) {
                HomeScreen(onShowClick = { id -> navController.navigate("detail/$id") })
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(onShowClick = { id -> navController.navigate("detail/$id") })
            }
            composable(Screen.Device.route) {
                DeviceScreen()
            }
            composable(
                route = "detail/{showId}",
                arguments = listOf(navArgument("showId") { type = NavType.IntType })
            ) {
                DetailScreen(onBack = { navController.popBackStack() })
            }
        }

        if (showBottomBar) {
            CineBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
private fun CineBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = Color(0xFF1C1C1C),
            shadowElevation = 24.dp,
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavScreens.forEach { screen ->
                    CineNavItem(
                        screen = screen,
                        selected = currentRoute == screen.route,
                        onClick = { onNavigate(screen.route) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CineNavItem(
    screen: Screen,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 250),
        label = "nav_bg_${screen.route}"
    )
    val iconTint by animateColorAsState(
        targetValue = if (selected) Color.White else Color(0xFF757575),
        animationSpec = tween(durationMillis = 250),
        label = "nav_tint_${screen.route}"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = bgColor,
        modifier = Modifier.size(52.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.label,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
