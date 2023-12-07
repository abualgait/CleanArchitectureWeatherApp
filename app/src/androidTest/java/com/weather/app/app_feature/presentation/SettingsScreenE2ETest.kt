package com.weather.app.app_feature.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.lifecycleScope
import com.weather.app.di.AppModule
import com.weather.app.di.LocalModule
import com.weather.app.di.NetworkModule
import com.weather.app.presentation.MainActivity
import com.weather.app.presentation.navigation.MyApp
import com.weather.app.testtags.TestTags
import com.weather.app.theme.AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class, LocalModule::class, NetworkModule::class)
class SettingsScreenE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            AppTheme(
                isNetworkAvailable = false
            ) {
                MyApp(isNetworkAvailable = false, isDarkTheme = false)
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNoDataFound() {

        composeRule.apply {
            onNodeWithTag(TestTags.IconSearch).assertIsDisplayed()
            onNodeWithTag(TestTags.IconSearch).performClick()
            onNodeWithTag(TestTags.IconBack).assertIsDisplayed()

            onNodeWithTag(TestTags.SearchField).assertIsDisplayed()
            onNodeWithTag(TestTags.ClearIcon).assertIsNotDisplayed()
            onNodeWithTag(TestTags.RecentLocationsText).assertIsNotDisplayed()


            onNodeWithTag(TestTags.SearchField).performTextInput("Ca")
            onNodeWithTag(TestTags.ClearIcon).assertIsDisplayed()
            onNodeWithTag(TestTags.RecentLocationsText).assertIsNotDisplayed()

            onNodeWithTag(TestTags.SearchField).performTextInput("iro")

            waitUntilExactlyOneExists(hasTestTag(TestTags.SnackBar))
            activity.lifecycleScope.launch {
                delay(3000)
                onNodeWithTag(TestTags.SnackBar).assertIsDisplayed()
            }


        }

    }

}