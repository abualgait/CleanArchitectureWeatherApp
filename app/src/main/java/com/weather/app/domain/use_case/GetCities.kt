package com.weather.app.domain.use_case

import com.weather.app.domain.repository.AppRepository

class GetCities(
    private val repository: AppRepository,
) {
    suspend operator fun invoke() =
        repository.getCities()

}