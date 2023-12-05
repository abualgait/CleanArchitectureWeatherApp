package com.weather.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.app.data.data_source.remote.response.HourlyForecastData
import com.weather.app.data.data_source.remote.response.HourlyTemperature
import com.weather.app.extension.convertHourlyTimestamp
import com.weather.app.extension.getDrawable
import com.weather.app.theme.AppTheme


@Composable
fun HourlyForecastItem(
    hourlyForecastData: HourlyForecastData,
    onClick: (HourlyForecastData) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(80.dp)
                .padding(10.dp)
                .clickable { onClick(hourlyForecastData) }
        ) {
            Image(
                painter = painterResource(id = hourlyForecastData.weatherIcon.getDrawable()),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(

                text = "${((hourlyForecastData.temperature.value - 32) * 5 / 9)}°",
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = hourlyForecastData.dateTime.convertHourlyTimestamp(),
                style = MaterialTheme.typography.labelMedium, maxLines = 1
            )


        }
    }
}

@Preview
@Composable
fun HourlyForecastsPreview() {
    AppTheme(isNetworkAvailable = true) {
        val item = HourlyForecastData(
            dateTime = "2023-12-04T12:00:00",
            iconPhrase = "Sunny",
            weatherIcon = 1,
            temperature = HourlyTemperature(value = 78, unit = "Celsius"),
            link = "https://example.com/forecast/12:00"
        )

        HourlyForecastItem(item) {}
    }

}
