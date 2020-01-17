package com.txusmslabs.templateapp

import android.app.Application
import androidx.room.Room
import com.txusmslabs.templateapp.di.DaggerMyAppComponent
import com.txusmslabs.templateapp.di.MyAppComponent
import com.txusmslabs.templateapp.framework.data.database.MovieDatabase

class MyApp : Application() {

    lateinit var db: MovieDatabase
        private set

    lateinit var component: MyAppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyAppComponent
            .factory()
            .create(this)
    }
}