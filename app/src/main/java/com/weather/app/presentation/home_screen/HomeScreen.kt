package com.weather.app.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weather.app.R
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.HourlyTemperature
import com.weather.app.data.data_source.remote.response.Temperature
import com.weather.app.data.data_source.remote.response.TemperatureUnit
import com.weather.app.data.data_source.remote.response.WeatherData
import com.weather.app.extension.getDrawable
import com.weather.app.extension.toFormattedDateString
import com.weather.app.extension.toFormattedTimeString
import com.weather.app.presentation.components.DailyForecastItem
import com.weather.app.presentation.components.HourlyForecastItem
import com.weather.app.presentation.settings_screen.navigation.SettingsScreenDestination
import com.weather.app.testtags.TestTags
import com.weather.app.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val loading = viewModel.loading.value
    val isDarkTheme = viewModel.isDarkTheme.value
    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState, modifier = Modifier.testTag(TestTags.SnackBar)) }
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(Icons.Filled.LocationOn, "LocationOn")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = viewModel.currentCityKey.value.split("-")[0],
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(Icons.Filled.ArrowDropDown, "ArrowDropDown")
                    Spacer(modifier = Modifier.weight(1f))

                    val brush = Brush.verticalGradient(
                        colors = listOf(
                            if (!isDarkTheme) Color(0xFF87CEEB) else Color(0xBA191970),
                            if (!isDarkTheme) Color(0xFF5EC1E9) else Color(0xE8000033)
                        )
                    )
                    CustomSwitch(
                        checked = isDarkTheme,
                        onCheckedChange = { darkTheme ->
                            viewModel.switchTheme(darkTheme)
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(40.dp)
                            .width(70.dp)
                            .clip(RoundedCornerShape(200.dp))
                            .background(brush = brush).testTag(TestTags.CustomSwitch)
                    )
                    Icon(Icons.Filled.Search, "Search", modifier = Modifier.clickable {
                        navController.navigate(SettingsScreenDestination.route)
                    }.testTag(TestTags.IconSearch))
                }
            }


            item {
                if (state.data.isNotEmpty()) {
                    WeatherCard(state.data.first())
                } else {
                    NoDataFound(viewModel, "No current conditions found")
                }
            }
            item {
                if (state.hourlyForecasts.isNotEmpty()) {
                    HourlyForecasts(state)
                } else {
                    NoDataFound(viewModel, "No hourly forecasts found")
                }
            }
            if (state.dailyForecasts.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Daily Forecasts",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(state.dailyForecasts) { item: DailyForecastData ->
                    DailyForecastItem(item) {

                    }
                }
            } else {
                item {
                    NoDataFound(viewModel, "No daily forecasts found")
                }
            }

        }

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading ...")
            }

        }
    }

}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    var animateOffset by remember { mutableStateOf(checked) }
    var offsetX by remember { mutableStateOf(0.dp) }

    val animatedOffsetX by animateFloatAsState(
        animationSpec = tween(durationMillis = 500),
        targetValue = if (animateOffset) 0f else 30f,
        finishedListener = {
            onCheckedChange(!checked)
        }, label = ""
    )

    offsetX = animatedOffsetX.dp

    Box(
        modifier = modifier
            .toggleable(
                value = checked,
                onValueChange = {
                    animateOffset = !animateOffset
                }
            )
            .padding(4.dp)
    ) {
        if (checked) {
            Image(
                painter = painterResource(id = R.drawable.sunny_night),
                contentDescription = "darkMode",
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = offsetX)
                    .testTag(TestTags.DarkModeImageTag)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = "lightMode",
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = offsetX)
                    .testTag(TestTags.LightModeImageTag)
            )
        }
    }
}

@Composable
private fun HourlyForecasts(state: HomeScreenState) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
        Text(text = "Hourly Forecasts", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow {
            items(state.hourlyForecasts) { hourlyForecast ->
                HourlyForecastItem(hourlyForecast) {

                }
            }
        }
    }
}


@Composable
fun WeatherCard(weatherData: WeatherData) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                if (weatherData.isDayTime) Color(0xFF87CEEB) else Color(0xBA191970),
                if (weatherData.isDayTime) Color(0xFF5EC1E9) else Color(0xE8000033)
            )
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopEnd)
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                if (weatherData.isDayTime) Color.White else Color.Gray,
                                if (weatherData.isDayTime) Color.Transparent else Color.Transparent
                            ),
                            center = center,
                            radius = size.width
                        ),
                        center = center,
                        radius = size.width
                    )
                }
                Image(
                    painter = painterResource(id = weatherData.weatherIcon.getDrawable()),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(45.dp)
                )
            }
            Box(modifier = Modifier.background(color = Color.Black.copy(alpha = 0.10f))) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = weatherData.weatherText,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${weatherData.temperature.metric.value}°${weatherData.temperature.metric.unit}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${weatherData.epochTime.toFormattedDateString()} - ${weatherData.epochTime.toFormattedTimeString()}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

        }
    }
}


@Composable
fun NoDataFound(viewModel: HomeScreenViewModel, title: String = "") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).testTag(TestTags.NoDataFound),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        viewModel.getAll()
                    }.testTag(TestTags.Reload + title)
            )
        }

    }

}


@Preview
@Composable
fun WeatherCardPreview() {
    AppTheme(isNetworkAvailable = true) {
        val sampleWeatherData = WeatherData(
            localObservationDateTime = "2023-12-03T12:36:00+02:00",
            epochTime = 1701599760,
            weatherText = "Mostly cloudy",
            weatherIcon = 6,
            hasPrecipitation = false,
            precipitationType = null,
            isDayTime = true,
            temperature = Temperature(
                metric = TemperatureUnit(23.6, "C", 17),
                imperial = TemperatureUnit(75.0, "F", 18)
            ),
            mobileLink = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us",
            link = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us"
        )

        WeatherCard(weatherData = sampleWeatherData)
    }

}

@Preview
@Composable
fun WeatherCardNightPreview() {
    AppTheme(isNetworkAvailable = true) {
        val sampleWeatherData = WeatherData(
            localObservationDateTime = "2023-12-03T12:36:00+02:00",
            epochTime = 1701599760,
            weatherText = "Mostly cloudy",
            weatherIcon = 40,
            hasPrecipitation = false,
            precipitationType = null,
            isDayTime = false,
            temperature = Temperature(
                metric = TemperatureUnit(23.6, "C", 17),
                imperial = TemperatureUnit(75.0, "F", 18)
            ),
            mobileLink = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us",
            link = "http://www.accuweather.com/en/eg/cairo/127164/current-weather/127164?lang=en-us"
        )

        WeatherCard(weatherData = sampleWeatherData)
    }

}

@Preview
@Composable
fun HourlyForecastsPreview() {
    AppTheme(isNetworkAvailable = true) {
        val hourlyForecastList: List<HourlyForecastData> = listOf(
            HourlyForecastData(
                dateTime = "2023-12-04T12:00:00",
                iconPhrase = "Sunny",
                weatherIcon = 1,
                temperature = HourlyTemperature(value = 25, unit = "Celsius"),
                link = "https://example.com/forecast/12:00"
            ),
            HourlyForecastData(
                dateTime = "2023-12-04T15:00:00",
                iconPhrase = "Partly Cloudy",
                weatherIcon = 3,
                temperature = HourlyTemperature(value = 22, unit = "Celsius"),
                link = "https://example.com/forecast/15:00"
            ),
            HourlyForecastData(
                dateTime = "2023-12-04T18:00:00",
                iconPhrase = "Cloudy",
                weatherIcon = 5,
                temperature = HourlyTemperature(value = 18, unit = "Celsius"),
                link = "https://example.com/forecast/18:00"
            )
        )

        HourlyForecasts(state = HomeScreenState(hourlyForecasts = hourlyForecastList))
    }

}

@Preview
@Composable
fun CustomSwitchPreview() {
    AppTheme(isNetworkAvailable = true) {
        var isDarkTheme by remember {
            mutableStateOf(false)
        }
        val brush = Brush.verticalGradient(
            colors = listOf(
                if (!isDarkTheme) Color(0xFF87CEEB) else Color(0xBA191970),
                if (!isDarkTheme) Color(0xFF5EC1E9) else Color(0xE8000033)
            )
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CustomSwitch(
                checked = isDarkTheme,
                onCheckedChange = {
                    //do your logic here
                    isDarkTheme = !isDarkTheme
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(40.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(200.dp))
                    .background(brush = brush)
            )
        }

    }

}
