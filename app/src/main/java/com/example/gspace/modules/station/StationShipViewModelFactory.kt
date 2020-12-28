package com.example.gspace.modules.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gspace.application.AppConfig

class StationShipViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StationViewModel::class.java)) {
            return StationViewModel(
                AppConfig.appComponent.getStationRepo(),
                AppConfig.appComponent.getStationDao()
            ) as T
        }
        throw IllegalArgumentException("No model class found!!")
    }

}