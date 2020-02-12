package com.txusmslabs.templateapp

import android.app.Application

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}