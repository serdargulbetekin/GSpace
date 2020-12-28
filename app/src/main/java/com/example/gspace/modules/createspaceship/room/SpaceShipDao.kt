package com.example.gspace.modules.createspaceship.room

import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface SpaceShipDao {

    @Query("SELECT * FROM space_ship")
    fun getSpaceShipList(): List<SpaceShipEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceship(spaceShipEntity: SpaceShipEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpaceship(spaceShipEntity: SpaceShipEntity)

    @Delete
    fun deleteSpaceShip(spaceShipEntity: SpaceShipEntity)

}