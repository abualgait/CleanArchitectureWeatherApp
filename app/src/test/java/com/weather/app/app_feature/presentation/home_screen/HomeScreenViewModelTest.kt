package com.weather.app.app_feature.presentation.home_screen

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.app_feature.MainDispatcherRule
import com.weather.app.data.data_source.local.WeatherDataStore
import com.weather.app.data.data_source.remote.response.Temperature
import com.weather.app.data.data_source.remote.response.TemperatureUnit
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.data.repository.AppRepositoryImpl
import com.weather.app.domain.DataState
import com.weather.app.domain.use_case.AppUseCases
import com.weather.app.domain.use_case.GetCurrentConditions
import com.weather.app.presentation.home_screen.HomeScreenEvent
import com.weather.app.presentation.home_screen.HomeScreenState
import com.weather.app.presentation.home_screen.HomeScreenViewModel
import com.weather.app.util.ConnectivityManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeScreenViewModelTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var homeScreenViewModel: HomeScreenViewModel


    @MockK
    lateinit var appUseCases: AppUseCases


    @MockK
    lateinit var connectivityManager: ConnectivityManager

    @MockK
    lateinit var dataStore: WeatherDataStore


    @MockK
    lateinit var mockAppRepositoryImpl: AppRepositoryImpl


    @Before
    fun setUp() {
        every {
            dataStore.getDarkThemePrefs()
        } returns flowOf(true)

        every {
            dataStore.getCityPrefs()
        } returns flowOf("Cairo-1")

        every {
            connectivityManager.isNetworkAvailable.value
        } returns true

        every {
            appUseCases.getCurrentConditions
        } returns GetCurrentConditions(mockAppRepositoryImpl)

        homeScreenViewModel = HomeScreenViewModel(appUseCases, connectivityManager, dataStore)

    }

    @Test
    fun testGetTheme_ReturnTrue() = runBlocking {
        every {
            dataStore.getDarkThemePrefs()
        } returns flowOf(true)

        dataStore.getDarkThemePrefs().test {
            val item = awaitItem()
            awaitComplete()
            assertThat(item).isTrue()
        }

    }

    @Test
    fun testGetTheme_ReturnFalse() = runBlocking {
        every {
            dataStore.getDarkThemePrefs()
        } returns flowOf(false)

        dataStore.getDarkThemePrefs().test {
            val item = awaitItem()
            awaitComplete()
            assertThat(item).isFalse()
        }
    }

    @Test
    fun testGetCurrentConditions_NoInternet_ReturnFalse() = runBlocking {
        //Given
        every {
            connectivityManager.isNetworkAvailable.value
        } returns false

        homeScreenViewModel.eventFlow.test {
            //When
            homeScreenViewModel.onEvent(HomeScreenEvent.GetCurrentConditions("1"))
            val item = awaitItem()
            //Then
            assertThat(item).isEqualTo(
                HomeScreenViewModel.UiEvent.ShowSnackbar("No network available")
            )

        }
    }

    @Test
    fun testGetCurrentConditions_HasInternet_ReturnTrue() = runTest {
        every {
            connectivityManager.isNetworkAvailable.value
        } returns true

        //Given
        val sampleWeatherData = WeatherData(
            localObservationDateTime = "2023-12-03T12:36:00+02:00",
            epochTime = 1701599760,
            weatherText = "Mostly cloudy",
            weatherIcon = 40,
            hasPrecipitation = false,
            precipitationType = null,
            isDayTime = false,
            temperature = Temperature(
                metric = TemperatureUnit(23.6, "C", 17),
                imperial = TemperatureUnit(75.0, "F", 18)
            ),
            mobileLink = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us",
            link = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us"
        )

        //When
        coEvery {
            appUseCases.getCurrentConditions.invoke("1")
        } returns flow {
            emit(DataState.loading())
            emit(DataState(data = listOf(sampleWeatherData)))
        }

        homeScreenViewModel.onEvent(HomeScreenEvent.GetCurrentConditions("1"))

        coVerify {
            appUseCases.getCurrentConditions.invoke("1")
        }

        //Then
        assertThat(homeScreenViewModel.loading.value).isEqualTo(
            false
        )
        assertThat(homeScreenViewModel.state.value).isEqualTo(
            HomeScreenState(
                data = listOf(
                    sampleWeatherData
                )
            )
        )
    }


    @Test
    fun testGetCurrentConditions_HasInternet_ReturnError() = runTest {
        //Given
        every {
            connectivityManager.isNetworkAvailable.value
        } returns true

        coEvery {
            appUseCases.getCurrentConditions.invoke("1")
        } returns flow {
            emit(DataState.loading())
            emit(DataState(error = "error"))
        }

        homeScreenViewModel.eventFlow.test {
            //When
            homeScreenViewModel.onEvent(HomeScreenEvent.GetCurrentConditions("1"))

            coVerify {
                appUseCases.getCurrentConditions.invoke("1")
            }

            //Then
            assertThat(homeScreenViewModel.loading.value).isEqualTo(
                false
            )
            val item = awaitItem()
            assertThat(item).isEqualTo(
                HomeScreenViewModel.UiEvent.ShowSnackbar("error")
            )
        }

    }

}