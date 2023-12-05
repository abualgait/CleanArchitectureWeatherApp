package com.weather.app.data.data_source.remote


import com.weather.app.data.data_source.remote.response.AdministrativeArea
import com.weather.app.data.data_source.remote.response.CityDataDTO
import com.weather.app.data.data_source.remote.response.Country
import com.weather.app.domain.DomainMapper
import com.weather.app.domain.model.City


class CityDtoMapper : DomainMapper<CityDataDTO, City> {
    override fun mapToDomainModel(model: CityDataDTO): City {
        return City(
            id = model.key,
            localizedName = model.localizedName,
            country = model.country.localizedName,
            administrativeArea = model.administrativeArea.localizedName
        )
    }

    override fun mapFromDomainModel(domainModel: City): CityDataDTO {
        return CityDataDTO(
            key = domainModel.id,
            localizedName = domainModel.localizedName,
            country = Country(localizedName = domainModel.country),
            administrativeArea = AdministrativeArea(localizedName = domainModel.administrativeArea)
        )
    }

    fun fromEntityList(initial: List<CityDataDTO>): List<City> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<City>): List<CityDataDTO> {
        return initial.map { mapFromDomainModel(it) }
    }


}






