package com.example.gspace.application

import android.app.Application

class GSpaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
    }
}