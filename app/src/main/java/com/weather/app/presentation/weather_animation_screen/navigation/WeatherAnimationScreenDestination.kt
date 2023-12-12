package com.weather.app.presentation.weather_animation_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.weather.app.navigation.AppNavigationDestination
import com.weather.app.presentation.weather_animation_screen.AnimationScreen

object AnimationScreenDestination : AppNavigationDestination {
    override val route = "animation_screen_route"
    override val destination = "animation_screen_destination"
}


fun NavGraphBuilder.animationScreen() {
    composable(route = AnimationScreenDestination.route) {
        AnimationScreen()
    }
}
