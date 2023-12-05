package com.weather.app.data.data_source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DailyForecastResponse(
    @Json(name = "DailyForecasts")
    val dailyForecasts: List<DailyForecastData>
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class DailyForecastData(
    @Json(name = "Date")
    val date: String,
    @Json(name = "Temperature")
    val temperature: ForecastTemperature,
    @Json(name = "Day")
    val day: Day,
    @Json(name = "Link")
    val link: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class ForecastTemperature(
    @Json(name = "Minimum")
    val minimum: Minimum,
    @Json(name = "Maximum")
    val maximum: Maximum
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Minimum(
    @Json(name = "Value")
    val value: Int,
    @Json(name = "Unit")
    val unit: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Maximum(
    @Json(name = "Value")
    val value: Int,
    @Json(name = "Unit")
    val unit: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Day(
    @Json(name = "Icon")
    val icon: Int,
    @Json(name = "IconPhrase")
    val iconPhrase: String
) : Parcelable
