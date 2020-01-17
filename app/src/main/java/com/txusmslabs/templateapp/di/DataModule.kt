package com.txusmslabs.templateapp.di

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.data.repository.PermissionChecker
import com.txusmslabs.data.repository.RegionRepository
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.LocationDataSource
import com.txusmslabs.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}