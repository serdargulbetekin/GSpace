package com.example.gspace.modules.station

import android.annotation.SuppressLint
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


    private var ugs = 0
    private var eus = 0
    private var ds = 0

    private val _stationListMutableLiveData = MutableLiveData<List<StationAdapterItem>>()

    val stationList: LiveData<List<StationAdapterItem>>
        get() = _stationListMutableLiveData

    private val _spaceship = MutableLiveData<SpaceShipEntity?>()

    val spaceShip: LiveData<SpaceShipEntity?>
        get() = _spaceship

    private val _currentStation = MutableLiveData<StationEntity?>()

    val currentStation: LiveData<StationEntity?>
        get() = _currentStation

    private val stationEntityList = mutableListOf<StationEntity>()

    init {
        requestStations()
    }

    private val hasItemList = mutableListOf<Boolean>()

    @SuppressLint("CheckResult")
    fun requestStations() {
        Singles.zip(
            Single.fromCallable { spaceShipDao.getSpaceShipList() }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()),
            stationRepo.singleStations().flatMap { pairStation ->
                Single.fromCallable {
                    pairStation.first.forEach {
                        if (stationDao.getStation(it.name) != null) {
                            hasItemList.add(true)
                        } else {
                            hasItemList.add(false)
                        }
                    }
                    Triple(pairStation.first, hasItemList.toList(), pairStation.second)
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        ) { spaceShipList, triple ->
            spaceShipList to triple
        }.map {

            val spaceShipEntityList = it.first
            val stationEntityList = it.second.first
            val hasFavList = it.second.second
            this._currentStation.postValue(it.second.third)

            this.stationEntityList.addAll(stationEntityList)

            val adapterItemList = mutableListOf<StationAdapterItem>()
            stationEntityList.forEachIndexed { index, stationEntity ->
                adapterItemList.add(
                    StationAdapterItem(
                        stationEntity,
                        hasFavList[index],
                        isCurrent = index == 0
                    )
                )
            }
            adapterItemList to spaceShipEntityList.lastOrNull()
        }
            .subscribe({
                _stationListMutableLiveData.postValue(it.first)
                _spaceship.postValue(it.second)
            }, {
                _stationListMutableLiveData.postValue(null)
            })
    }

    private fun singleFavList(): Single<Pair<List<StationEntity>, List<Boolean>>> {
        hasItemList.clear()
        return Single.fromCallable {
            stationEntityList.forEach {
                if (stationDao.getStation(it.name) != null) {
                    hasItemList.add(true)
                } else {
                    hasItemList.add(false)
                }
            }
            stationEntityList.toList() to hasItemList.toList()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    @SuppressLint("CheckResult")
    fun onImageViewClick(stationEntity: StationEntity) {
        Single.fromCallable {
            val station = stationDao.getStation(stationEntity.name)
            if (station != null) {
                stationDao.deleteWithName(stationEntity.name)
            } else {
                stationDao.insertStation(stationEntity)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap {
            singleFavList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }.map {
            val adapterItemList = mutableListOf<StationAdapterItem>()
            stationEntityList.forEachIndexed { index, stationEntity ->
                adapterItemList.add(
                    StationAdapterItem(
                        stationEntity,
                        it.second[index],
                        isCurrent = currentStation.value?.name == stationEntity.name
                    )
                )
            }
            adapterItemList.toList()
        }.subscribe({
            _stationListMutableLiveData.postValue(it)
        }, {
            _stationListMutableLiveData.postValue(null)
        })

    }

    fun onTextViewTravelClick(stationEntity: StationEntity, onCurrentStationError: () -> Unit) {
        if (currentStation.value?.name == stationEntity.name) {
            onCurrentStationError.invoke()
        } else {
            this._currentStation.postValue(stationEntity)
            val stationAdapterItemList = mutableListOf<StationAdapterItem>()

            stationEntityList.forEachIndexed { index, stationEntityItem ->
                stationAdapterItemList.add(
                    StationAdapterItem(
                        StationEntity(
                            name = stationEntityItem.name,
                            coordinateX = stationEntityItem.coordinateX,
                            coordinateY = stationEntityItem.coordinateY,
                            capacity = stationEntityItem.capacity,
                            stock = stationEntityItem.stock,
                            need = stationEntityItem.need,
                            distance = stationRepo.calculateDistance(
                                stationEntity.coordinateX,
                                stationEntity.coordinateY,
                                stationEntityItem.coordinateX,
                                stationEntityItem.coordinateY
                            )
                        ), hasItemList[index],
                        isCurrent = stationEntityItem.name == stationEntity.name
                    )
                )
            }

            stationEntityList.clear()
            stationAdapterItemList.forEach {
                stationEntityList.add(it.stationEntity)

            }

            _stationListMutableLiveData.postValue(stationAdapterItemList)
        }

    }


}