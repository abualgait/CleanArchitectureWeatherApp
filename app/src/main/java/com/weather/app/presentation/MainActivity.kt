package com.weather.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.weather.app.data.data_source.local.WeatherDataStore
import com.weather.app.presentation.components.DisposableEffectWithLifeCycle
import com.weather.app.presentation.navigation.MyApp
import com.weather.app.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var dataStore: WeatherDataStore

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            DisposableEffectWithLifeCycle(
                onStart = {
                    connectivityManager.registerConnectionObserver(this)
                },
                onDestroy = {
                    connectivityManager.registerConnectionObserver(this)
                }
            )
            var darkThemeState by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                dataStore.getDarkThemePrefs().collect {
                    darkThemeState = it
                }
            }
            MyApp(
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                isDarkTheme = darkThemeState
            )
        }
    }
}
