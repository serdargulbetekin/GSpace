package com.example.gspace.modules.createspaceship.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "space_ship")
data class SpaceShipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "endurance")
    val endurance: String,
    @ColumnInfo(name = "velocity")
    val velocity: String,
    @ColumnInfo(name = "capacity")
    val capacity: String
)