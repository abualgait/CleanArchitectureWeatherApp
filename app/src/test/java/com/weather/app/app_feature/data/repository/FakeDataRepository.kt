package com.weather.app.app_feature.data.repository

import com.weather.app.data.data_source.local.CityEntity
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.domain.DataState
import com.weather.app.domain.model.City
import com.weather.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class FakeDataRepository(
    private val retrofitService: RetrofitService
) : AppRepository {

    private val data = mutableListOf<City>()


    override fun getSearchResults(query: String): Flow<DataState<List<City>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCityOffline(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCity(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun getCities(): Flow<List<CityEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentConditions(locationId: String): Flow<DataState<List<WeatherData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyForecasts(locationId: String): Flow<DataState<List<HourlyForecastData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyForecasts(locationId: String): Flow<DataState<List<DailyForecastData>>> {
        TODO("Not yet implemented")
    }
}