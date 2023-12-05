package com.weather.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.weather.app.data.data_source.local.WeatherDataStore
import com.weather.app.presentation.navigation.MyApp
import com.weather.app.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager


    @Inject
    lateinit var dataStore: WeatherDataStore


    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    private var darkThemeState = mutableStateOf(false)

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            lifecycleScope.launch {
                dataStore.getDarkThemePrefs().collect {
                    darkThemeState.value = it
                }
            }
            MyApp(
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                isDarkTheme = darkThemeState.value
            )
        }
    }
}
