package com.txusmslabs.templateapp.data.server

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
//        @Query("region") region: String
    ): Response<MovieDbResult>
}