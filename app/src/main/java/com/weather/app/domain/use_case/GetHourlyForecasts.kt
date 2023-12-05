package com.weather.app.domain.use_case

import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.domain.DataState
import com.weather.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetHourlyForecasts(
    private val repository: AppRepository
) {

    suspend operator fun invoke(locationId: String): Flow<DataState<List<HourlyForecastData>>> {
        return repository.getHourlyForecasts(locationId)
    }
}