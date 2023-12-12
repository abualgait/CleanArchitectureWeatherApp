package com.weather.app.presentation.search_screen

import com.weather.app.domain.model.City

sealed class SearchScreenEvent {
    data class CitiesSearchResults(val query: String) : SearchScreenEvent()
    data class CitySelection(val city: City) : SearchScreenEvent()
    data class DeleteCity(val city: City) : SearchScreenEvent()
    data object DisplaySelectedCities : SearchScreenEvent()

}

