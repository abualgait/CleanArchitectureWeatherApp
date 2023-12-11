package com.weather.app.presentation.search_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.weather.app.navigation.AppNavigationDestination
import com.weather.app.presentation.search_screen.SearchScreen

object SearchScreenDestination : AppNavigationDestination {
    override val route = "search_screen_route"
    override val destination = "search_screen_destination"
}

fun NavGraphBuilder.searchScreen(navController: NavController) {
    composable(
        route = SearchScreenDestination.route
    ) {
        SearchScreen(
            navController = navController,
        )
    }
}
