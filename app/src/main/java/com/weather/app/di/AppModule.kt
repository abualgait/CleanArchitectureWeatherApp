package com.weather.app.di

import com.weather.app.domain.repository.AppRepository
import com.weather.app.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository): AppUseCases {
        return AppUseCases(
            getSearchResults = GetSearchResults(repository),
            getCurrentConditions = GetCurrentConditions(repository),
            addCityOffline = AddCityOffline(repository),
            getCities = GetCities(repository),
            deleteCity = DeleteCity(repository),
            hourlyForecasts = GetHourlyForecasts(repository),
            dailyForecasts = GetDailyForecasts(repository)
        )
    }
}