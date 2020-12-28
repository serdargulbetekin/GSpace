package com.example.gspace.modules.createspaceship

import android.app.Application
import android.content.Context
import com.example.gspace.modules.createspaceship.room.SpaceShipDao
import com.example.gspace.modules.createspaceship.room.SpaceShipDatabase
import com.example.gspace.modules.createspaceship.room.SpaceShipEntity

class CreateSpaceShipRepo(context: Context) {

    private var dao: SpaceShipDao? = null

    init {
        val db = SpaceShipDatabase.invoke(context)
        dao = db.spaceShipDao()
    }

    fun getSpaceShip() = dao?.getSpaceShipList() ?: listOf()

    fun insertSpaceShip(spaceShipEntity: SpaceShipEntity) = dao?.insertSpaceship(spaceShipEntity)

    fun deleteSpaceShip(spaceShipEntity: SpaceShipEntity) = dao?.deleteSpaceShip(spaceShipEntity)

    fun updateSpaceShip(spaceShipEntity: SpaceShipEntity) = dao?.updateSpaceship(spaceShipEntity)
}