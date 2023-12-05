package com.weather.app.util

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class ConnectivityManager @Inject constructor(application: Application) {
    private val connectionFlow = ConnectionFlowCallback(application)

    // observe this in ui
    val isNetworkAvailable = mutableStateOf(false)


    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            connectionFlow.startNetworkCallback().collect { isConnected ->
                isNetworkAvailable.value = isConnected
            }
        }
    }
}