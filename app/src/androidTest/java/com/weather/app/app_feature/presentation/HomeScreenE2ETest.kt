package com.weather.app.app_feature.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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
class HomeScreenE2ETest {

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

    @Test
    fun tearDown() {


    }

    @Test
    fun testCustomSwitch() {
        composeRule.activity.lifecycleScope.launch {
            delay(1000)
            composeRule.onNodeWithTag(TestTags.LightModeImageTag).assertIsDisplayed()
            composeRule.onNodeWithTag(TestTags.CustomSwitch).performClick()
            delay(300)
            composeRule.onNodeWithTag(TestTags.DarkModeImageTag).assertIsDisplayed()
        }

    }

    @Test
    fun testSearchIconNavigation() {
        composeRule.onNodeWithTag(TestTags.IconSearch).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.IconSearch).performClick()
        composeRule.onNodeWithTag(TestTags.IconBack).assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNoDataFound() {
        composeRule.onAllNodesWithTag(TestTags.NoDataFound).assertCountEquals(3)
        composeRule.onNodeWithTag(TestTags.Reload + "No current conditions found").performClick()
        composeRule.waitUntilExactlyOneExists(hasTestTag(TestTags.SnackBar))
        composeRule.onNodeWithTag(TestTags.SnackBar).assertIsDisplayed()

    }

}