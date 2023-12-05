package com.weather.app.app_feature.domain.use_case

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.weather.app.app_feature.data.repository.FakeDataRepository
import com.weather.app.data.data_source.remote.RetrofitService
import com.weather.app.domain.model.City
import com.weather.app.domain.use_case.GetSearchResults
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GetListOfDataTest {

    private lateinit var getListOfData: GetSearchResults
    private lateinit var fakeRepository: FakeDataRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private lateinit var retrofitService: RetrofitService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/getEntitys/")
        retrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(RetrofitService::class.java)
        fakeRepository = FakeDataRepository(retrofitService)
        getListOfData = GetSearchResults(fakeRepository)

        val entitysToInsert = mutableListOf<City>()
        (1..10).forEachIndexed { index, num ->
            entitysToInsert.add(
                City(index.toString(), "Cairo", "Egypt", "Cairo")
            )
        }
        entitysToInsert.shuffle()
        runBlocking {
            entitysToInsert.forEach { fakeRepository.addCityOffline(it) }
        }
    }


}