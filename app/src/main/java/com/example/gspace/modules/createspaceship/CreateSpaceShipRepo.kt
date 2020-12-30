package com.example.gspace.modules.createspaceship

import com.example.gspace.application.AppConfig
import com.example.gspace.modules.createspaceship.room.SpaceShipEntity

class CreateSpaceShipRepo {

    private val dao by lazy { AppConfig.appComponent.getSpaceShipDao() }

    fun getSpaceShip() = dao.getSpaceShipList()

    fun insertSpaceShip(spaceShipEntity: SpaceShipEntity) = dao.insertSpaceship(spaceShipEntity)

    fun deleteSpaceShip(spaceShipEntity: SpaceShipEntity) = dao.deleteSpaceShip(spaceShipEntity)

    fun updateSpaceShip(spaceShipEntity: SpaceShipEntity) = dao.updateSpaceship(spaceShipEntity)
}