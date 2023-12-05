package com.weather.app.domain.use_case

data class AppUseCases(
    val getSearchResults: GetSearchResults,
    val addCityOffline: AddCityOffline,
    val deleteCity: DeleteCity,
    val getCities: GetCities,
    val getCurrentConditions: GetCurrentConditions,
    val hourlyForecasts: GetHourlyForecasts,
    val dailyForecasts: GetDailyForecasts,
)
