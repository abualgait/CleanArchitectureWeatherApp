package com.weather.app.presentation.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.data.data_source.local.WeatherDataStore
import com.weather.app.data.data_source.local.fromEntityList
import com.weather.app.domain.use_case.AppUseCases
import com.weather.app.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val connectivityManager: ConnectivityManager,
    private val dataStore: WeatherDataStore,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val query = savedStateHandle.get<String>("q")
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state


    var loading = mutableStateOf(false)
        private set

    var currentLocationId = mutableStateOf("")
        private set

    var isConnected = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dataStore.getCityPrefs().collect {
                currentLocationId.value = it.split("-")[1]
                onEvent(SearchScreenEvent.DisplaySelectedCities)
            }
        }

    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.CitiesSearchResults -> {
                viewModelScope.launch {
                    doSearch(event)
                }
            }

            is SearchScreenEvent.CitySelection -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        appUseCases.addCityOffline(event.city)
                    }
                    dataStore.setCityPrefs("${event.city.localizedName}, ${event.city.country}-${event.city.id}")
                }
            }

            SearchScreenEvent.DisplaySelectedCities -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appUseCases.getCities.invoke().collect {
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                selectedCities = it.fromEntityList()
                            )
                        }
                    }
                }

            }

            is SearchScreenEvent.DeleteCity -> {
                viewModelScope.launch {
                    appUseCases.deleteCity(event.city)
                }
            }
        }
    }

    private suspend fun doSearch(event: SearchScreenEvent.CitiesSearchResults) {
        if (!connectivityManager.isNetworkAvailable.value) {
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "No network available"
                )
            )
            return
        }
        appUseCases.getSearchResults.invoke(event.query)
            .onEach { data ->
                loading.value = data.loading
                if (data.data != null) {
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            data = data.data
                        )
                    }

                }
                if (data.error != null) {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = data.error

                        )
                    )
                }
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}