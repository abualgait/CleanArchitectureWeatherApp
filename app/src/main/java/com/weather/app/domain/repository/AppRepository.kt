package com.weather.app.domain.repository

import com.weather.app.data.data_source.local.CityEntity
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.domain.DataState
import com.weather.app.domain.model.City
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getSearchResults(query: String): Flow<DataState<List<City>>>
    suspend fun addCityOffline(city: City)
    suspend fun deleteCity(city: City)
    suspend fun getCities(): Flow<List<CityEntity>>
    suspend fun getCurrentConditions(locationId: String): Flow<DataState<List<WeatherData>>>
    suspend fun getHourlyForecasts(locationId: String): Flow<DataState<List<HourlyForecastData>>>
    suspend fun getDailyForecasts(locationId: String): Flow<DataState<List<DailyForecastData>>>
}