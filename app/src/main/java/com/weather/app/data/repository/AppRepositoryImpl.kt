package com.weather.app.data.repository

import com.weather.app.data.data_source.local.AppDao
import com.weather.app.data.data_source.local.CityEntity
import com.weather.app.data.data_source.local.mapFromDomainModel
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.data.data_source.remote.fromEntityList
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.domain.DataState
import com.weather.app.domain.model.City
import com.weather.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(
    private val dao: AppDao,
    private val retrofitService: RetrofitService,
) : AppRepository {

    override fun getSearchResults(query: String): Flow<DataState<List<City>>> = flow {
        try {
            emit(DataState.loading())
            val response = retrofitService.autoCompleteSearch(q = query)
            emit(DataState.success(response.fromEntityList()))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun addCityOffline(city: City) {
        dao.insertEntity(city.mapFromDomainModel())
    }

    override suspend fun deleteCity(city: City) {
        dao.deleteEntity(city.mapFromDomainModel())
    }


    override suspend fun getCities(): Flow<List<CityEntity>> {
        return dao.getEntities()
    }

    override suspend fun getCurrentConditions(locationId: String): Flow<DataState<List<WeatherData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService.currentConditions(locationId)
                emit(DataState.success(response))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getHourlyForecasts(locationId: String): Flow<DataState<List<HourlyForecastData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService.getHourlyForecastByCityCode(locationId)
                emit(DataState.success(response))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getDailyForecasts(locationId: String): Flow<DataState<List<DailyForecastData>>> =
        flow {
            try {
                emit(DataState.loading())
                val response = retrofitService.getDailyForecastByCityCode(locationId)
                emit(DataState.success(response.dailyForecasts))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }

}