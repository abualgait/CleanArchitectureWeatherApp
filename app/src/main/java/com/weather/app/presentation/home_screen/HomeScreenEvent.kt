package com.weather.app.presentation.home_screen

sealed class HomeScreenEvent {
    data class GetCurrentConditions(val locationId: String) : HomeScreenEvent()
    data class GetHourlyForecasts(val locationId: String) : HomeScreenEvent()
    data class GetDailyForecasts(val locationId: String) : HomeScreenEvent()
}
