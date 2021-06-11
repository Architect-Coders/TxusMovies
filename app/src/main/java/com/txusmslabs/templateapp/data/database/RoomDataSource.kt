package com.txusmslabs.templateapp.data.database

import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie
import com.txusmslabs.templateapp.framework.data.toRoomMovie

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() <= 0

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.map { it.toRoomMovie() })
    }

    override suspend fun getPopularMovies(): List<Movie> =
        movieDao.getAll().map { it.toDomainMovie() }

    override suspend fun findById(id: Int): Movie? = movieDao.findById(id)?.toDomainMovie()

    override suspend fun update(movie: Movie): Boolean =
        movieDao.updateMovie(movie.toRoomMovie()) > 0
}