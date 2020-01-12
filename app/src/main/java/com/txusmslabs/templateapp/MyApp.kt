package com.txusmslabs.templateapp

import android.app.Application
import androidx.room.Room
import com.txusmslabs.templateapp.framework.data.database.MovieDatabase

class MyApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}