package com.weather.app.presentation.home_screen

import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData

data class HomeScreenState(
    val data: List<WeatherData> = emptyList(),
    val hourlyForecasts: List<HourlyForecastData> = emptyList(),
    val dailyForecasts: List<DailyForecastData> = emptyList(),
)