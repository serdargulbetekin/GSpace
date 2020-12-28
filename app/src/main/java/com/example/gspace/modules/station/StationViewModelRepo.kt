package com.example.gspace.modules.station

import com.example.gspace.api.RestApi
import com.example.gspace.modules.station.room.StationEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONArray

class StationRepo(
    private val restApi: RestApi
) {

    fun singleStations(): Observable<List<StationEntity>> {
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
        return spaceStationList.toList()
    }
}

