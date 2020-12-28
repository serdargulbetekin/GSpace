package com.example.gspace.modules.station

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gspace.modules.station.room.StationDao
import com.example.gspace.modules.station.room.StationEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StationViewModel @Inject constructor(
    private val stationRepo: StationRepo,
    private val stationDao: StationDao
) : ViewModel() {

    private val _stationListMutableLiveData = MutableLiveData<List<StationAdapterItem>>()

    val stationList: LiveData<List<StationAdapterItem>>
        get() = _stationListMutableLiveData

    init {
        requestStations()
    }

    @SuppressLint("CheckResult")
    fun requestStations() {
        stationRepo.singleStations().flatMap {
            val hasItemList = mutableListOf<Boolean>()
            Observable.fromCallable {
                it.forEach {
                    if (stationDao.getStation(it.name) != null) {
                        hasItemList.add(true)
                    } else {
                        hasItemList.add(false)
                    }
                }
                it to hasItemList
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }.map { pair ->
            val adapterItemList = mutableListOf<StationAdapterItem>()
            pair.first.forEachIndexed { index, stationEntity ->
                adapterItemList.add(
                    StationAdapterItem(
                        stationEntity,
                        pair.second[index]
                    )
                )
            }


            adapterItemList
        }
            .subscribe({
                _stationListMutableLiveData.postValue(it)
            }, {
                _stationListMutableLiveData.postValue(null)
            })
    }

    @SuppressLint("CheckResult")
    fun onImageViewClick(stationEntity: StationEntity) {
        Single.fromCallable {
            val station = stationDao.getStation(stationEntity.name)

            if (station != null) {
                stationDao.deleteStation(stationEntity)
            } else {
                stationDao.insertStation(stationEntity)
            }

        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            Log.d("Serdar", "ASLKFASFLA")

        }, {
            Log.d("Serdar", it.message.toString())
        })

    }

    fun onTextViewTravelClick(stationEntity: StationEntity) {

    }


}