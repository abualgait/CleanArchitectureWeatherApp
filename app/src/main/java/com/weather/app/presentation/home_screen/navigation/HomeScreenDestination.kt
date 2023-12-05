package com.weather.app.presentation.home_screen.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.weather.app.navigation.AppNavigationDestination
import com.weather.app.presentation.home_screen.HomeScreen

object HomeScreenDestination : AppNavigationDestination {
    override val route = "home_screen_route"
    override val destination = "home_screen_destination"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(route = HomeScreenDestination.route) {
        HomeScreen(
            navController = navController
        )
    }
}
