package com.weather.app.app_feature.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weather.app.app_feature.data.repository.FakeWeatherAppRepository
import com.weather.app.app_feature.responses.MockWebServerResponses
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.domain.use_case.GetDailyForecasts
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class GetDailyForecastsTest {

    private lateinit var getDailyForecasts: GetDailyForecasts
    private lateinit var fakeRepository: FakeWeatherAppRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private lateinit var retrofitService: RetrofitService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("forecasts/v1/daily/5day/")
        retrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(RetrofitService::class.java)
        fakeRepository =
            FakeWeatherAppRepository(retrofitService)
        getDailyForecasts = GetDailyForecasts(fakeRepository)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun callDailyForecasts_ReturnSuccess() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.dailyForecastResponse)
        )

        val result = getDailyForecasts.invoke("").toList()

        assertThat(result[0].loading).isTrue()
        val hourlyForecasts = result[1].data
        assertThat(hourlyForecasts?.get(0)?.temperature?.minimum?.value).isEqualTo(20)
        assertThat(hourlyForecasts?.get(0)?.temperature?.maximum?.value).isEqualTo(30)
        assertThat(hourlyForecasts).isNotNull()
        assertThat(hourlyForecasts!!.size).isEqualTo(3)

    }

    @Test
    fun callDailyForecasts_ReturnHttpError() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val result = getDailyForecasts.invoke("").toList()

        assertThat(result[0].loading).isTrue()
        val error = result[1].error
        assertThat(error).isNotNull()

    }


}