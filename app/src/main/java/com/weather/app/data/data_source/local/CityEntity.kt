package com.weather.app.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_entity")
data class CityEntity(
    @PrimaryKey val id: String,
    val localizedName: String,
    val country: String,
    val administrativeArea: String
)