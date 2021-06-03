package com.txusmslabs.templateapp.framework.data.server

import android.util.Log
import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(
        apiKey: String,
        region: String
    ): Either<Failure, List<Movie>> {

        try {

            return theMovieDb.service
                .listPopularMoviesAsync(apiKey, region).safeCall(transform = {
                    it.results.mapNotNull { m ->
                        try {

                            m.toDomainMovie()
                        } catch (e: Exception) {
                            Log.e("fff", e.localizedMessage, e)
                            null
                        }

                    }
                })
        } catch (e: Exception) {
            Log.e("getPopularMovies", e.localizedMessage, e)
            return Either.Left(Failure.UnexpectedFailure)
        }

    }
}