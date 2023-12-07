package com.weather.app.app_feature.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.weather.app.app_feature.data.repository.FakeWeatherAppRepository
import com.weather.app.domain.model.City
import com.weather.app.domain.use_case.AddCityOffline
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddCityOfflineTest {

    private lateinit var addCityOffline: AddCityOffline
    private lateinit var fakeRepository: FakeWeatherAppRepository

    @Before
    fun setUp() {
        fakeRepository =
            FakeWeatherAppRepository()
        addCityOffline = AddCityOffline(fakeRepository)

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


}