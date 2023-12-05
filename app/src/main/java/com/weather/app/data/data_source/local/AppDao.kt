package com.weather.app.data.data_source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM city_entity")
    fun getEntities(): Flow<List<CityEntity>>

    @Query("SELECT * FROM city_entity WHERE id = :id")
    suspend fun getEntityById(id: Int): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(entity: CityEntity)

    @Delete
    suspend fun deleteEntity(entity: CityEntity)
}