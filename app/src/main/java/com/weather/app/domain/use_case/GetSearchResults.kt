package com.weather.app.domain.use_case

import com.weather.app.domain.DataState
import com.weather.app.domain.model.City
import com.weather.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResults(
    private val repository: AppRepository,
) {
    operator fun invoke(query: String): Flow<DataState<List<City>>> =
        repository.getSearchResults(query)

}