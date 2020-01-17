package com.txusmslabs.usecases

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie? = moviesRepository.findById(id)
}