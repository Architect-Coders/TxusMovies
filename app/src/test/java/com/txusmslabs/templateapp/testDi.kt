package com.txusmslabs.templateapp

import com.txusmslabs.data.functional.Either
import com.txusmslabs.data.repository.PermissionChecker
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.LocationDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.domain.Movie
import com.txusmslabs.testshared.mockedMovie
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var movies: List<Movie> = emptyList()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun getPopularMovies(): List<Movie> = movies

    override suspend fun findById(id: Int): Movie? = movies.firstOrNull { it.id == id }

    override suspend fun update(movie: Movie): Boolean {
        movies = movies.filterNot { it.id == movie.id } + movie
        return true
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    private var movies = defaultFakeMovies

    override suspend fun getMovies(apiKey: String, region: String) = Either.Right(movies)
}

class FakeLocationDataSource : LocationDataSource {
    private var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    private var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}
