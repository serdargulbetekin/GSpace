package com.example.gspace.modules.createspaceship.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gspace.application.AppConfig
import com.example.gspace.modules.createspaceship.CreateSpaceShipViewModel
import com.example.gspace.modules.favorites.FavoritesViewModel
import com.example.gspace.modules.station.StationViewModel

class Factory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CreateSpaceShipViewModel::class.java) -> {
                CreateSpaceShipViewModel(AppConfig.appComponent.getCreateShipRepo()) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(AppConfig.appComponent.getStationDao()) as T
            }
            modelClass.isAssignableFrom(StationViewModel::class.java) -> {
                StationViewModel(
                    AppConfig.appComponent.getStationRepo(),
                    AppConfig.appComponent.getStationDao(),
                    AppConfig.appComponent.getSpaceShipDao()
                ) as T
            }
            else -> throw IllegalArgumentException("No View Model Class Found!!!")
        }
    }

}