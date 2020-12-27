package com.example.gspace.modules.createspaceship.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class SpaceShipDatabase : RoomDatabase() {
    abstract fun spaceShipDao(): SpaceShipDao

    companion object {

        @Volatile
        private var instance: SpaceShipDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildRoomDatabase(context).also { instance = it }
        }

        private fun buildRoomDatabase(context: Context) =
            Room.databaseBuilder(context, SpaceShipDatabase::class.java, "space_ship.db").build()
    }
}