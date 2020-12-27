package com.example.gspace.application

import android.content.Context
import com.example.gspace.di.AppComponent
import com.example.gspace.di.AppModule
import com.example.gspace.di.DaggerAppComponent


object AppConfig {

    private lateinit var context: Context
    lateinit var appComponent: AppComponent


    fun init(context: Context) {
        this.context = context
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(AppConfig.context))
            .build()

    }
}
