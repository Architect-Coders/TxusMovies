package com.txusmslabs.data.repository

import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {
    suspend fun suspendPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int): Movie? = localDataSource.findById(id)

    suspend fun update(movie: Movie): Movie? {
        return if (localDataSource.update(movie)) movie else null
    }
}
