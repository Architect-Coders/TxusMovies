package com.txusmslabs.templateapp.framework.data.database

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    suspend fun findById(id: Int): Movie?

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Update
    suspend fun updateMovie(movie: Movie): Int
}