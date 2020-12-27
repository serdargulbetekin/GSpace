package com.example.gspace.modules.main

import com.example.gspace.api.RestApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONArray

class MainActivityViewModelRepo(
    private val restApi: RestApi
) {

    fun singleStations(): Single<List<GSpaceStation>> {
        return restApi.getStations()
            .map {
                parseStations(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun parseStations(responseBody: ResponseBody): List<GSpaceStation> {
        val spaceStationList = mutableListOf<GSpaceStation>()
        val jsonArray = JSONArray(responseBody.string())

        for (x in 0 until jsonArray.length()) {
            val jsonObjectData = jsonArray.getJSONObject(x)
            spaceStationList.add(
                GSpaceStation(
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

data class GSpaceStation(
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    val stock: Int,
    val need: Int
)
