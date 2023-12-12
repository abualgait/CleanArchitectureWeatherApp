package com.weather.app.presentation.search_screen.navigation

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.weather.app.navigation.AppNavigationDestination
import com.weather.app.presentation.search_screen.SearchScreen

object SearchScreenDestination : AppNavigationDestination {
    override val route = "search_screen_route"
    override val destination = "search_screen_destination"
}

fun NavGraphBuilder.searchScreen(navController: NavController) {
    composable(
        route = SearchScreenDestination.route + "/{q}",
        arguments = listOf(
            navArgument("q") {
                type = NavType.StringType
                defaultValue = "default"
            }
        ),
        deepLinks = listOf(navDeepLink {
            uriPattern = "https://weatherapp.com/{q}"
            action = Intent.ACTION_VIEW
        })
    ) {
        val bundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(
            "bundleKey"
        )
        val qureyFromBundle = bundle?.getString("q")
        val qureyFromArguments = it.arguments?.getString("q")

        SearchScreen(
            navController = navController,
            q = qureyFromBundle
        )
    }
}
