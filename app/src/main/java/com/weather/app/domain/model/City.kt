package com.weather.app.domain.model


data class City(
    val id: String,
    val localizedName: String,
    val country: String,
    val administrativeArea: String,
    val isSelected: Boolean = false
)

class InvalidDataException(message: String) : Exception(message)