package com.weather.app.data.data_source.local


import com.weather.app.domain.model.City


fun CityEntity.mapToDomainModel(): City {
    return City(
        id = id,
        localizedName = localizedName,
        country = country,
        administrativeArea = administrativeArea,

        )
}

fun City.mapFromDomainModel(): CityEntity {
    return CityEntity(
        id = id,
        localizedName = localizedName,
        country = country,
        administrativeArea = administrativeArea
    )
}

fun List<CityEntity>.fromEntityList(): List<City> {
    return  map { it.mapToDomainModel() }
}

fun List<City>.toEntityList(): List<CityEntity> {
    return map { it.mapFromDomainModel() }
}









