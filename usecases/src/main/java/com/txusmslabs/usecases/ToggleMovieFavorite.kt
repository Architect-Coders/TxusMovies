package com.txusmslabs.usecases

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also {
            moviesRepository.update(it)
        }
    }
}