package com.weather.app.data.data_source.remote

import com.weather.app.data.data_source.remote.response.CityDataDTO
import com.weather.app.data.data_source.remote.response.DailyForecastResponse
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "hNUBVNitjctSGngA0yAbbGVyjY4QBljs"

interface RetrofitService {

    @GET("locations/v1/cities/autocomplete")
    suspend fun autoCompleteSearch(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("q") q: String = "",
    ): List<CityDataDTO>

    @GET("currentconditions/v1/{locationId}")
    suspend fun currentConditions(
        @Path("locationId") locationId: String = "",
        @Query("apikey") apiKey: String = API_KEY,
    ): List<WeatherData>

    @GET("forecasts/v1/hourly/12hour/{cityId}")
    suspend fun getHourlyForecastByCityCode(
        @Path("cityId") cityId: String,
        @Query("apikey") apikey: String = API_KEY
    ): List<HourlyForecastData>


    @GET("forecasts/v1/daily/5day/{cityId}")
    suspend fun getDailyForecastByCityCode(
        @Path("cityId") cityId: String,
        @Query("apikey") apikey: String = API_KEY
    ): DailyForecastResponse
}