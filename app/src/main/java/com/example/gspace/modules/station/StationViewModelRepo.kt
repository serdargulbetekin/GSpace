package com.example.gspace.modules.station

import com.example.gspace.api.RestApi
import com.example.gspace.modules.station.room.StationEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONArray
import kotlin.math.pow

class StationRepo(
    private val restApi: RestApi
) {

    fun singleStations(): Single<List<StationEntity>> {
        return restApi.getStations()
            .map {
                parseStations(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun parseStations(responseBody: ResponseBody): List<StationEntity> {
        val spaceStationList = mutableListOf<StationEntity>()
        val jsonArray = JSONArray(responseBody.string())

        for (x in 0 until jsonArray.length()) {
            val jsonObjectData = jsonArray.getJSONObject(x)
            spaceStationList.add(
                StationEntity(
                    name = jsonObjectData.optString("name"),
                    coordinateX = jsonObjectData.optDouble("coordinateX"),
                    coordinateY = jsonObjectData.optDouble("coordinateY"),
                    capacity = jsonObjectData.optInt("capacity"),
                    stock = jsonObjectData.optInt("stock"),
                    need = jsonObjectData.optInt("need")
                )
            )
        }
        val world = spaceStationList.firstOrNull { it.name == "Dünya" }
        val distanceMap = mutableMapOf<String, Double>()
        spaceStationList.forEach {
            val distance = calculateDistance(
                world?.coordinateX ?: 0.0,
                world?.coordinateY ?: 0.0,
                it.coordinateX,
                it.coordinateY
            )
            distanceMap[it.name] = distance
        }
        spaceStationList.forEach {
            it.distance = distanceMap[it.name] ?: 0.0

        }
        return spaceStationList.toList()
    }

    private fun calculateDistance(
        coorX1: Double,
        coorY1: Double,
        coorX2: Double,
        coorY2: Double
    ): Double {
        return (coorX1 - coorX2).pow(2) + (coorY1 - coorY2).pow(2)
    }
}

