package com.txusmslabs.usecases

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.domain.Movie

class GetPopularMovies(
    private val mainRepository: MoviesRepository
) {
    fun flow() = mainRepository.getMovies()
}