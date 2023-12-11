package com.weather.app.data.data_source.remote


import com.weather.app.data.data_source.remote.response.AdministrativeArea
import com.weather.app.data.data_source.remote.response.CityDataDTO
import com.weather.app.data.data_source.remote.response.Country
import com.weather.app.domain.model.City


fun CityDataDTO.mapToDomainModel(): City {
    return City(
        id = key,
        localizedName = localizedName,
        country = country.localizedName,
        administrativeArea = administrativeArea.localizedName
    )
}

fun City.mapFromDomainModel(): CityDataDTO {
    return CityDataDTO(
        key = id,
        localizedName = localizedName,
        country = Country(localizedName = country),
        administrativeArea = AdministrativeArea(localizedName = administrativeArea)
    )
}

fun List<CityDataDTO>.fromEntityList(): List<City> {
    return map { it.mapToDomainModel() }
}

fun List<City>.toEntityList(): List<CityDataDTO> {
    return map { it.mapFromDomainModel() }
}









