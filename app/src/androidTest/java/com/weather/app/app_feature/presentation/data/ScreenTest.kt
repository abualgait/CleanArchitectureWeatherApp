package com.weather.app.app_feature.presentation.data

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.weather.app.di.AppModule
import com.weather.app.di.LocalModule
import com.weather.app.di.NetworkModule
import com.weather.app.presentation.MainActivity
import com.weather.app.presentation.navigation.MyApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(AppModule::class, LocalModule::class, NetworkModule::class)
class ScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            MyApp(isNetworkAvailable = true, isDarkTheme = false)
        }
    }


}