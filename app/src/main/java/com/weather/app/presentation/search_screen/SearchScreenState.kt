package com.weather.app.presentation.search_screen

import com.weather.app.domain.model.City

data class SearchScreenState(
    val data: List<City> = emptyList(),
    val selectedCities: List<City> = emptyList()
)