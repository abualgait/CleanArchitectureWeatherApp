package com.weather.app.presentation.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weather.app.navigation.AppTopLevelNavigation
import com.weather.app.navigation.TOP_LEVEL_DESTINATIONS
import com.weather.app.navigation.TopLevelDestination
import com.weather.app.presentation.home_screen.navigation.HomeScreenDestination
import com.weather.app.presentation.home_screen.navigation.homeScreen
import com.weather.app.presentation.search_screen.navigation.SearchScreenDestination
import com.weather.app.presentation.search_screen.navigation.searchScreen
import com.weather.app.presentation.settings_screen.navigation.settingsScreen
import com.weather.app.presentation.weather_animation_screen.navigation.animationScreen
import com.weather.app.theme.AppTheme

@Composable
fun MyApp(isNetworkAvailable: Boolean, isDarkTheme: Boolean) {
    AppTheme(
        isNetworkAvailable = isNetworkAvailable,
        darkTheme = isDarkTheme,
    ) {
        val navController = rememberNavController()
        val appTopLevelNavigation = remember(navController) {
            AppTopLevelNavigation(navController)
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            bottomBar = {
                AppBottomBar(
                    onNavigateToTopLevelDestination = appTopLevelNavigation::navigateTo,
                    currentDestination = currentDestination
                )
            }
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {

                NavHost(
                    navController = navController,
                    startDestination = HomeScreenDestination.route,
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .zIndex(1f)
                ) {
                    homeScreen(navController = navController)
                    searchScreen(navController = navController)
                    settingsScreen()
                    animationScreen()
                }
            }
        }
    }

}

@Composable
private fun AppBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            tonalElevation = 0.dp
        ) {

            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick =
                    {

                        onNavigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(
                            if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}

