package com.weather.app.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weather.app.data.data_source.local.AppDatabase
import com.weather.app.data.data_source.local.CityEntityMapper
import com.weather.app.data.data_source.remote.CityDtoMapper
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.data.repository.AppRepositoryImpl
import com.weather.app.domain.repository.AppRepository
import com.weather.app.domain.use_case.*
import com.weather.app.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            AppDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideService(okHttpClient: OkHttpClient, mosh: Moshi): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(mosh))
            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        db: AppDatabase, retrofitService: RetrofitService,
        dtoMapper: CityDtoMapper,
        entityMapper: CityEntityMapper,
    ): AppRepository {
        return AppRepositoryImpl(db.appDao, retrofitService, dtoMapper, entityMapper)
    }

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

    @Provides
    @Singleton
    fun provideCityDtoMapper() = CityDtoMapper()


    @Provides
    @Singleton
    fun provideCityEntityMapper() = CityEntityMapper()

}