package com.example.gspace.modules.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val mainActivityViewModelRepo: MainActivityViewModelRepo) :
    ViewModel() {

    private val stationListMutableLiveData = MutableLiveData<List<GSpaceStation>>()

    val _stationList: LiveData<List<GSpaceStation>>
        get() = stationListMutableLiveData

    @SuppressLint("CheckResult")
    fun requestStations() {
        mainActivityViewModelRepo.singleStations().subscribe({
            stationListMutableLiveData.postValue(it)
        }, {
            stationListMutableLiveData.postValue(null)
        })
    }


}