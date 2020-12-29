package com.example.gspace.modules.station

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gspace.modules.createspaceship.room.SpaceShipDao
import com.example.gspace.modules.createspaceship.room.SpaceShipEntity
import com.example.gspace.modules.station.room.StationDao
import com.example.gspace.modules.station.room.StationEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StationViewModel @Inject constructor(
    private val stationRepo: StationRepo,
    private val stationDao: StationDao,
    private val spaceShipDao: SpaceShipDao
) : ViewModel() {

    private val _stationListMutableLiveData = MutableLiveData<List<StationAdapterItem>>()

    val stationList: LiveData<List<StationAdapterItem>>
        get() = _stationListMutableLiveData

    private val _spaceship = MutableLiveData<SpaceShipEntity?>()

    val spaceShip: LiveData<SpaceShipEntity?>
        get() = _spaceship

    init {
        requestStations()
    }

    @SuppressLint("CheckResult")
    fun requestStations() {
        Singles.zip(
            Single.fromCallable { spaceShipDao.getSpaceShipList() }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()),
            stationRepo.singleStations().flatMap {
                val hasItemList = mutableListOf<Boolean>()
                Single.fromCallable {
                    it.forEach {
                        if (stationDao.getStation(it.name) != null) {
                            hasItemList.add(true)
                        } else {
                            hasItemList.add(false)
                        }
                    }
                    it to hasItemList.toList()
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        ) { spaceShipList, pair ->
            spaceShipList to pair
        }.map {
            val adapterItemList = mutableListOf<StationAdapterItem>()
            it.second.first.forEachIndexed { index, stationEntity ->
                adapterItemList.add(
                    StationAdapterItem(
                        stationEntity,
                        it.second.second[index]
                    )
                )
            }
            adapterItemList to it.first.lastOrNull()
        }
            .subscribe({
                _stationListMutableLiveData.postValue(it.first)
                _spaceship.postValue(it.second)
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

        }.flatMap {
            Single.fromCallable {
                requestStations()
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