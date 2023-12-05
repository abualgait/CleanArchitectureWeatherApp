package com.weather.app.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.data.data_source.local.WeatherDataStore
import com.weather.app.domain.use_case.AppUseCases
import com.weather.app.util.ConnectivityManager
import com.weather.app.util.DialogQueue
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
class HomeScreenViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val connectivityManager: ConnectivityManager,
    private val dataStore: WeatherDataStore
) : ViewModel() {

    val dialogQueue = DialogQueue()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var loading = mutableStateOf(false)
        private set

    var isDarkTheme = mutableStateOf(false)


    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    var currentCityKey = mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            dataStore.getDarkThemePrefs().collect {
                isDarkTheme.value = it
            }
        }
        getAll()

    }

    fun getAll() {
        viewModelScope.launch {
            dataStore.getCityPrefs().collect {
                currentCityKey.value = it
                val id = it.split("-")[1]
                onEvent(HomeScreenEvent.GetCurrentConditions(id))
                onEvent(HomeScreenEvent.GetHourlyForecasts(id))
                onEvent(HomeScreenEvent.GetDailyForecasts(id))
            }

        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.GetCurrentConditions -> {
                viewModelScope.launch { getCurrentConditions(event.locationId) }
            }

            is HomeScreenEvent.GetDailyForecasts -> {
                viewModelScope.launch { getDailyForecasts(event.locationId) }
            }

            is HomeScreenEvent.GetHourlyForecasts -> {
                viewModelScope.launch { getHourlyForecasts(event.locationId) }
            }
        }
    }

    private suspend fun getCurrentConditions(locationId: String) {
        if (!connectivityManager.isNetworkAvailable.value) {
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "No network available"
                )
            )
            return
        }
        appUseCases.getCurrentConditions.invoke(locationId)
            .onEach { data ->
                loading.value = true
                if (data.data != null) {
                    loading.value = false
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            data = data.data
                        )
                    }
                }
                if (data.error != null) {
                    loading.value = false
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = data.error
                        )
                    )
                }
            }.launchIn(viewModelScope)

    }

    private suspend fun getHourlyForecasts(locationId: String) {
        if (!connectivityManager.isNetworkAvailable.value) {
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "No network available"
                )
            )
            return
        }
        appUseCases.hourlyForecasts.invoke(locationId)
            .onEach { data ->
                loading.value = true
                if (data.data != null) {
                    loading.value = false
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            hourlyForecasts = data.data
                        )
                    }
                }
                if (data.error != null) {
                    loading.value = false
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = data.error
                        )
                    )
                }
            }.launchIn(viewModelScope)

    }

    private suspend fun getDailyForecasts(locationId: String) {
        if (!connectivityManager.isNetworkAvailable.value) {
            _eventFlow.emit(
                UiEvent.ShowSnackbar(
                    message = "No network available"
                )
            )
            return
        }
        appUseCases.dailyForecasts.invoke(locationId)
            .onEach { data ->
                loading.value = true
                if (data.data != null) {
                    loading.value = false
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            dailyForecasts = data.data
                        )
                    }
                }
                if (data.error != null) {
                    loading.value = false
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = data.error
                        )
                    )
                }
            }.launchIn(viewModelScope)

    }

    fun switchTheme(newState: Boolean) {
        viewModelScope.launch { dataStore.setDarkThemePrefs(newState) }

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}