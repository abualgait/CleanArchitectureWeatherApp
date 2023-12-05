package com.weather.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.weather.app.data.data_source.remote.response.DailyForecastData
import com.weather.app.extension.getDayOfWeek
import com.weather.app.extension.getDrawable


@Composable
fun DailyForecastItem(
    dailyForecastData: DailyForecastData,
    onClick: (DailyForecastData) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .clickable { onClick(dailyForecastData) }
        ) {
            Image(
                painter = painterResource(id = dailyForecastData.day.icon.getDrawable()),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = dailyForecastData.day.iconPhrase,
                    style = MaterialTheme.typography.titleSmall, maxLines = 1
                )
                Text(
                    text = dailyForecastData.date.getDayOfWeek(),
                    style = MaterialTheme.typography.titleSmall, maxLines = 1
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${((dailyForecastData.temperature.maximum.value - 32) * 5 / 9)} / ${((dailyForecastData.temperature.minimum.value - 32) * 5 / 9)} °C",
                style = MaterialTheme.typography.titleSmall, maxLines = 1
            )


        }

    }

}