package com.weather.app.presentation.settings_screen

import com.weather.app.domain.model.City

sealed class SettingsScreenEvent {
    data class CitiesSearchResults(val query: String) : SettingsScreenEvent()
    data class CitySelection(val city: City) : SettingsScreenEvent()
    data class DeleteCity(val city: City) : SettingsScreenEvent()
    data object DisplaySelectedCities : SettingsScreenEvent()

}

