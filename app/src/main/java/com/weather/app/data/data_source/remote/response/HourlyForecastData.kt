package com.weather.app.data.data_source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class HourlyForecastData(
    @Json(name = "DateTime")
    val dateTime: String,
    @Json(name = "IconPhrase")
    val iconPhrase: String,
    @Json(name = "WeatherIcon")
    val weatherIcon: Int,
    @Json(name = "Temperature")
    val temperature: HourlyTemperature,
    @Json(name = "Link")
    val link: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class HourlyTemperature(
    @Json(name = "Value")
    val value: Int,
    @Json(name = "Unit")
    val unit: String
) : Parcelable


