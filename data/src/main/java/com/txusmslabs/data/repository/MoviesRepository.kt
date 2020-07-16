package com.txusmslabs.data.repository

import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {
    suspend fun suspendPopularMovies(): Either<Failure, List<Movie>> {
        if (localDataSource.isEmpty()) {
            test()
            val movies =
                remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())

            movies.getLeft()?.let { return movies }

            localDataSource.saveMovies(movies.getRight())
        }
        return Either.Right(localDataSource.getPopularMovies())
    }

    suspend fun findById(id: Int): Movie? = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)

    private fun test() {

    }
}
