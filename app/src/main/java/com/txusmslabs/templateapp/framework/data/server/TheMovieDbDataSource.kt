package com.txusmslabs.templateapp.framework.data.server

import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}