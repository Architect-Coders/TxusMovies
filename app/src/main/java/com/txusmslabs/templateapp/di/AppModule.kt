package com.txusmslabs.templateapp.di

import android.app.Application
import androidx.room.Room
import com.txusmslabs.data.repository.PermissionChecker
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.LocationDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.framework.data.AndroidPermissionChecker
import com.txusmslabs.templateapp.framework.data.PlayServicesLocationDataSource
import com.txusmslabs.templateapp.framework.data.database.MovieDatabase
import com.txusmslabs.templateapp.framework.data.database.RoomDataSource
import com.txusmslabs.templateapp.framework.data.server.TheMovieDbDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}