package com.weather.app.app_feature.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.weather.app.app_feature.data.repository.FakeWeatherAppRepository
import com.weather.app.data.data_source.local.mapFromDomainModel
import com.weather.app.data.repository.AppRepositoryImpl
import com.weather.app.domain.model.City
import com.weather.app.domain.use_case.AddCityOffline
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddCityOfflineTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var addCityOffline: AddCityOffline
    private lateinit var addCityOfflineMockk: AddCityOffline

    private lateinit var fakeRepository: FakeWeatherAppRepository

    @MockK
    private lateinit var mockRepository: AppRepositoryImpl

    @Before
    fun setUp() {
        fakeRepository = FakeWeatherAppRepository()
        addCityOffline = AddCityOffline(fakeRepository)

        addCityOfflineMockk = AddCityOffline(mockRepository)

    }

    @Test
    fun addCityOffline_ReturnSuccess() = runBlocking {
        val city =
            City(id = "1", localizedName = "Cairo", country = "Egypt", administrativeArea = "CA")
        addCityOffline(city = city)
        val result = fakeRepository.getCities().first()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].id).isEqualTo("1")
        assertThat(result[0].localizedName).isEqualTo("Cairo")
    }

    @Test
    fun mockkAddCityOffline_ReturnSuccess() = runBlocking {

        val city =
            City(id = "1", localizedName = "Cairo", country = "Egypt", administrativeArea = "CA")

        coEvery { mockRepository.addCityOffline(city) } just runs
        addCityOfflineMockk(city = city)

        coEvery { mockRepository.getCities() } returns flowOf(
            listOf(
                city.mapFromDomainModel()
            )
        )

        val result = mockRepository.getCities().first()
        coVerify {
            mockRepository.addCityOffline(city)
            mockRepository.getCities()
        }
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].id).isEqualTo("1")
        assertThat(result[0].localizedName).isEqualTo("Cairo")
    }


}