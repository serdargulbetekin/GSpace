package com.example.gspace.modules.station.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StationEntity::class], version = 1)
abstract class StationDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao

    companion object {
        @Volatile
        private var instance: StationDatabase? = null

        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildRoomDatabase(context).also { instance = it }
        }

        private fun buildRoomDatabase(context: Context) =
            Room.databaseBuilder(context, StationDatabase::class.java, "station.db").build()

    }
}