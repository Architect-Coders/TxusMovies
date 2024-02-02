package com.txusmslabs.templateapp.data.server

import android.util.Log
import com.txusmslabs.data.exception.Failure
import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.framework.data.toDomainMovie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb, private val apiKey: String) : RemoteDataSource {

    override suspend fun getMovies(
        page: Int
//        apiKey: String,
//        region: String
    ): Either<Failure, List<Movie>> {

        try {

            return theMovieDb.service
                .listPopularMoviesAsync(apiKey, page).safeCall(transform = {
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