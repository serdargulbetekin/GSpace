package com.example.gspace.modules.favorites

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gspace.modules.station.room.StationDao
import com.example.gspace.modules.station.room.StationEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val stationDao: StationDao) : ViewModel() {

    private val _mutableFavLiveData = MutableLiveData<List<StationEntity>>()

    val favLiveData: LiveData<List<StationEntity>>
        get() = _mutableFavLiveData

    init {
        getFavList()
    }

    @SuppressLint("CheckResult")
    private fun getFavList() {
        Single.fromCallable {
            stationDao.getStationList()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mutableFavLiveData.postValue(it)
            }, {
                _mutableFavLiveData.postValue(null)

            })

    }

    @SuppressLint("CheckResult")
    fun deleteStationEntity(stationEntity: StationEntity) {
        Single.fromCallable {
            stationDao.deleteStation(stationEntity)
        }.flatMap {
                Single.fromCallable {
                    getFavList()
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Serdar: ", "Deleted")
            }, {
                Log.d("Serdar: ", it.message.toString())

            })
    }
}