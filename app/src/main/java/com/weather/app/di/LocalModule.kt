package com.weather.app.di

import android.app.Application
import androidx.room.Room
import com.weather.app.data.data_source.local.AppDatabase
import com.weather.app.data.data_source.local.CityEntityMapper
import com.weather.app.data.data_source.remote.CityDtoMapper
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.data.repository.AppRepositoryImpl
import com.weather.app.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {


    @Provides
    @Singleton
    fun provideCityDtoMapper() = CityDtoMapper()


    @Provides
    @Singleton
    fun provideCityEntityMapper() = CityEntityMapper()


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEntityRepository(
        db: AppDatabase,
        retrofitService: RetrofitService,
        dtoMapper: CityDtoMapper,
        entityMapper: CityEntityMapper,
    ): AppRepository {
        return AppRepositoryImpl(db.appDao, retrofitService, dtoMapper, entityMapper)
    }

}