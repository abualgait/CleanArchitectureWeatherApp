package com.weather.app.presentation.home_screen

import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    sealed class Success : HomeScreenState() {
        data class Weather(val data: List<WeatherData>) : Success()
        data class Hourly(val hourlyForecasts: List<HourlyForecastData>) : Success()
        data class Daily(val dailyForecasts: List<DailyForecastData>) : Success()
    }

    data class Error(val error: String) : HomeScreenState()
}