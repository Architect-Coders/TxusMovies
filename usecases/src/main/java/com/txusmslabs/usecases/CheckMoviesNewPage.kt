package com.txusmslabs.usecases

import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.domain.Movie

class CheckMoviesNewPage(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(lastVisible: Int): Failure? =
        moviesRepository.checkRequiredNewPage(lastVisible)
}