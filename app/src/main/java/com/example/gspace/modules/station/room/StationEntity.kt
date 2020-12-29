package com.example.gspace.modules.station.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station")
data class StationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    val stock: Int,
    val need: Int,
    var distance:Double=0.0
)

