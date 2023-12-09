package com.weather.app.app_feature.presentation.data.locale

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.weather.app.data.data_source.local.AppDao
import com.weather.app.data.data_source.local.AppDatabase
import com.weather.app.data.data_source.local.CityEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDaoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDatabase: AppDatabase
    private lateinit var appDao: AppDao

    @Before
    fun setUp() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        appDao = weatherDatabase.appDao
    }

    @After
    fun tearDown() {
        weatherDatabase.close()
    }

    @Test
    fun insertCity_ReturnSuccess() = runTest {
        val city = CityEntity("1", "Cairo", "Egypt", "Cairo")
        appDao.insertEntity(city)
        assertThat(appDao.getEntities().first()).isEqualTo(arrayListOf(city))

    }

    @Test
    fun deleteCity_ReturnSuccess() = runTest {
        val city = CityEntity("1", "Cairo", "Egypt", "Cairo")
        appDao.insertEntity(city)
        assertThat(appDao.getEntities().first()).isEqualTo(arrayListOf(city))
        appDao.deleteEntity(city)
        assertThat(appDao.getEntities().first().size).isEqualTo(0)
    }

    @Test
    fun getCity_ReturnSuccess() = runTest {
        val city = CityEntity("1", "Cairo", "Egypt", "Cairo")
        appDao.insertEntity(city)
        val result = appDao.getEntityById(1)
        assertThat(result).isNotNull()
        assertThat(result?.id).isEqualTo("1")
    }
}