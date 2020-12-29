package com.example.gspace.api


import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RestApi {

    @GET("https://run.mocky.io/v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    fun getStations(): Single<ResponseBody>


}