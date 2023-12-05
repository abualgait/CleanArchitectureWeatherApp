package com.weather.app.domain.use_case

import com.weather.app.domain.model.City
import com.weather.app.domain.repository.AppRepository

class DeleteCity(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(city: City) =
        repository.deleteCity(city)

}