package com.txusmslabs.templateapp.framework.data.server

import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}