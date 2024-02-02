package com.txusmslabs.data.source

import com.txusmslabs.domain.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun size(): Int
    suspend fun saveMovies(movies: List<Movie>)
    fun getMovies(): Flow<List<Movie>>
    suspend fun findById(id: Int): Movie?
    suspend fun update(movie: Movie): Boolean
}