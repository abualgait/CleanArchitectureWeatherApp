package com.weather.app.presentation.settings_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.weather.app.navigation.AppNavigationDestination
import com.weather.app.presentation.settings_screen.SettingsScreen

object SettingsScreenDestination : AppNavigationDestination {
    override val route = "settings_screen_route"
    override val destination = "settings_screen_destination"
}

fun NavGraphBuilder.settingsScreen(navController: NavController) {
    composable(
        route = SettingsScreenDestination.route
    ) {
        SettingsScreen(
            navController = navController,
        )
    }
}
