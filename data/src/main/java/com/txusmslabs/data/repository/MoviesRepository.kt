package com.txusmslabs.data.repository

import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
) {
    fun getMovies(): Flow<List<Movie>> = localDataSource.getMovies()
//        flow {
//        if (localDataSource.isEmpty()) {
//            test()
//            val movies =
//                remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
//
//            movies.getLeft()?.let { emit(emptyList<Movie>()) }
//
//            localDataSource.saveMovies(movies.getRight())
//        }
//        return Either.Right(localDataSource.getPopularMovies())
//    }

    suspend fun findById(id: Int): Movie? = localDataSource.findById(id)

    suspend fun checkRequiredNewPage(lastVisible: Int): Failure? {
        val size = localDataSource.size()

        if (lastVisible >= size - PAGE_THRESHOLD) {
            val page = size / PAGE_SIZE + 1
            val newMovies = remoteDataSource.getMovies(page)
            if (newMovies.isRight)
                localDataSource.saveMovies(newMovies.getRight())

            return newMovies.getLeft()
        }
        return null
    }

    suspend fun update(movie: Movie) = localDataSource.update(movie)

    companion object {
        private const val PAGE_SIZE = 20
        private const val PAGE_THRESHOLD = 10

    }
}
