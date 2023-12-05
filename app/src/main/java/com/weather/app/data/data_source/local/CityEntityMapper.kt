package com.weather.app.data.data_source.local


import com.weather.app.domain.DomainMapper
import com.weather.app.domain.model.City


class CityEntityMapper : DomainMapper<CityEntity, City> {
    override fun mapToDomainModel(model: CityEntity): City {
        return City(
            id = model.id,
            localizedName = model.localizedName,
            country = model.country,
            administrativeArea = model.administrativeArea,

            )
    }

    override fun mapFromDomainModel(domainModel: City): CityEntity {
        return CityEntity(
            id = domainModel.id,
            localizedName = domainModel.localizedName,
            country = domainModel.country,
            administrativeArea = domainModel.administrativeArea
        )
    }

    fun fromEntityList(initial: List<CityEntity>): List<City> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<City>): List<CityEntity> {
        return initial.map { mapFromDomainModel(it) }
    }


}






