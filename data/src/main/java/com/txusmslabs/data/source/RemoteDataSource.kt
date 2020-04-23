package com.txusmslabs.data.source

import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.functional.Either
import com.txusmslabs.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): Either<Failure, List<Movie>>
}