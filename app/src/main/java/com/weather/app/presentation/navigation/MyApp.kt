package com.weather.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.weather.app.presentation.home_screen.navigation.HomeScreenDestination
import com.weather.app.presentation.home_screen.navigation.homeScreen
import com.weather.app.presentation.settings_screen.navigation.settingsScreen
import com.weather.app.theme.AppTheme

@Composable
fun MyApp(isNetworkAvailable: Boolean, isDarkTheme: Boolean) {
    AppTheme(
        isNetworkAvailable = isNetworkAvailable,
        darkTheme = isDarkTheme,
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeScreenDestination.route
        ) {
            homeScreen(
                navController = navController
            )

            settingsScreen(
                navController = navController
            )
        }
    }

}
