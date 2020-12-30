package com.example.gspace.modules.station.room

import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface StationDao {

    @Query("SELECT * FROM station")
    fun getStationList(): List<StationEntity>

    @Query("SELECT * FROM station WHERE name==:name")
    fun getStation(name: String): StationEntity?

    @Query("DELETE FROM station WHERE name==:name")
    fun deleteWithName(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(station: StationEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStation(station: StationEntity)

    @Delete()
    fun deleteStation(station: StationEntity)

}