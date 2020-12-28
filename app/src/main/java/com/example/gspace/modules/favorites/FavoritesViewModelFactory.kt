package com.example.gspace.modules.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gspace.application.AppConfig
import java.lang.IllegalArgumentException

class FavoritesViewModelFactory :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(AppConfig.appComponent.getStationDao()) as T
        }
        throw IllegalArgumentException("No View Model class found.")
    }

}