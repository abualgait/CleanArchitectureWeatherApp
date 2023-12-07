package com.weather.app.app_feature.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weather.app.app_feature.data.repository.FakeWeatherAppRepository
import com.weather.app.app_feature.responses.MockWebServerResponses
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.domain.use_case.GetSearchResults
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

class GetSearchResultsTest {

    private lateinit var getSearchResults: GetSearchResults
    private lateinit var fakeRepository: FakeWeatherAppRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private lateinit var retrofitService: RetrofitService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("locations/v1/cities/autocomplete/")
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
        getSearchResults = GetSearchResults(fakeRepository)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun callGetResults_ReturnSuccess() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.searchResults)
        )

        val result = getSearchResults.invoke("").toList()

        assertThat(result[0].loading).isTrue()
        val cities = result[1].data
        assertThat(cities?.get(0)?.localizedName?.lowercase()).isEqualTo("Cairo".lowercase())
        assertThat(cities).isNotNull()
        assertThat(cities!!.size).isEqualTo(2)

    }

    @Test
    fun callGetResults_ReturnHttpError() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val result = getSearchResults.invoke("").toList()

        assertThat(result[0].loading).isTrue()
        val error = result[1].error
        assertThat(error).isNotNull()

    }


}