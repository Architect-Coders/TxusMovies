package com.txusmslabs.templateapp.framework.data.database

import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie
import com.txusmslabs.templateapp.framework.data.toRoomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { movieDao.movieCount() <= 0 }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) { movieDao.insertMovies(movies.map { it.toRoomMovie() }) }
    }

    override suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun findById(id: Int): Movie? = withContext(Dispatchers.IO) {
        movieDao.findById(id)?.toDomainMovie()
    }

    override suspend fun update(movie: Movie): Boolean =
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) > 0 }
}