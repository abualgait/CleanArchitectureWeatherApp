package com.weather.app.data.data_source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherData(
    @Json(name = "LocalObservationDateTime")
    val localObservationDateTime: String,
    @Json(name = "EpochTime")
    val epochTime: Long,
    @Json(name = "WeatherText")
    val weatherText: String,
    @Json(name = "WeatherIcon")
    val weatherIcon: Int,
    @Json(name = "HasPrecipitation")
    val hasPrecipitation: Boolean,
    @Json(name = "PrecipitationType")
    val precipitationType: String?,
    @Json(name = "IsDayTime")
    val isDayTime: Boolean,
    @Json(name = "Temperature")
    val temperature: Temperature,
    @Json(name = "MobileLink")
    val mobileLink: String,
    @Json(name = "Link")
    val link: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Temperature(
    @Json(name = "Metric")
    val metric: TemperatureUnit,
    @Json(name = "Imperial")
    val imperial: TemperatureUnit
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class TemperatureUnit(
    @Json(name = "Value")
    val value: Double,
    @Json(name = "Unit")
    val unit: String,
    @Json(name = "UnitType")
    val unitType: Int
) : Parcelable
