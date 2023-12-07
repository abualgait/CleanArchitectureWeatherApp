package com.weather.app.app_feature.data.repository

import com.weather.app.data.data_source.local.CityEntity
import com.weather.app.data.data_source.local.CityEntityMapper
import com.weather.app.data.data_source.remote.CityDtoMapper
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.domain.DataState
import com.weather.app.domain.model.City
import com.weather.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeWeatherAppRepository(
    private val retrofitService: RetrofitService? = null,
    private val dtoMapper: CityDtoMapper = CityDtoMapper(),
    private val entityMapper: CityEntityMapper = CityEntityMapper(),
) : AppRepository {

    private val citie = mutableListOf<City>()


    override fun getSearchResults(query: String): Flow<DataState<List<City>>> = flow {
        try {
            emit(DataState.loading())
            val response = retrofitService?.autoCompleteSearch(q = query)
            emit(DataState.success(dtoMapper.fromEntityList(response!!)))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }

    }

    override suspend fun addCityOffline(city: City) {
        citie.add(city)
    }

    override suspend fun deleteCity(city: City) {
        citie.remove(city)
    }

    override suspend fun getCities(): Flow<List<CityEntity>> {
        return flowOf(entityMapper.toEntityList(citie))
    }

    override suspend fun getCurrentConditions(locationId: String): Flow<DataState<List<WeatherData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService?.currentConditions("")
                emit(DataState.success(response ?: emptyList()))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getHourlyForecasts(locationId: String): Flow<DataState<List<HourlyForecastData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService?.getHourlyForecastByCityCode("")
                emit(DataState.success(response ?: emptyList()))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getDailyForecasts(locationId: String): Flow<DataState<List<DailyForecastData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService?.getDailyForecastByCityCode("")
                emit(DataState.success(response?.dailyForecasts ?: emptyList()))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }
}