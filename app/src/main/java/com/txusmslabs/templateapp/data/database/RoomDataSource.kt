package com.txusmslabs.templateapp.data.database

import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie
import com.txusmslabs.templateapp.framework.data.toRoomMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun size(): Int = movieDao.movieCount()

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.map { it.toRoomMovie() })
    }

    override fun getMovies(): Flow<List<Movie>> =
        movieDao.getAll().map { movies -> movies.map { it.toDomainMovie() } }

    override suspend fun findById(id: Int): Movie? = movieDao.findById(id)?.toDomainMovie()

    override suspend fun update(movie: Movie): Boolean =
        movieDao.updateMovie(movie.toRoomMovie()) > 0
}