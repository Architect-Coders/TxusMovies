package com.txusmslabs.data.source

import com.txusmslabs.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}