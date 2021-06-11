package com.txusmslabs.templateapp.framework.data

import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.data.database.Movie as DomainMovie
import com.txusmslabs.templateapp.data.server.Movie as ServerMovie

fun Movie.toRoomMovie(): DomainMovie =
    DomainMovie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

fun DomainMovie.toDomainMovie(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun ServerMovie.toDomainMovie(): Movie =
    Movie(
        0,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        "https://image.tmdb.org/t/p/w780$backdropPath",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )