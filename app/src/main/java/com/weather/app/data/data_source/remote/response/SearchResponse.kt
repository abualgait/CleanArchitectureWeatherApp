package com.weather.app.data.data_source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class CityDataDTO(
    @Json(name = "Version")
    val version: Int? = null,
    @Json(name = "Type")
    val type: String? = null,
    @Json(name = "Rank")
    val rank: Int? = null,
    @Json(name = "Key")
    val key: String,
    @Json(name = "LocalizedName")
    val localizedName: String,
    @Json(name = "Country")
    val country: Country,
    @Json(name = "AdministrativeArea")
    val administrativeArea: AdministrativeArea
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Country(
    @Json(name = "ID")
    val id: String? = null,
    @Json(name = "LocalizedName")
    val localizedName: String
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class AdministrativeArea(
    @Json(name = "ID")
    val id: String? = null,
    @Json(name = "LocalizedName")
    val localizedName: String
) : Parcelable