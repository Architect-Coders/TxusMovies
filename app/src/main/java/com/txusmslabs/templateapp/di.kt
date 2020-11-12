package com.txusmslabs.templateapp

import android.app.Application
import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.data.repository.PermissionChecker
import com.txusmslabs.data.repository.RegionRepository
import com.txusmslabs.data.source.LocalDataSource
import com.txusmslabs.data.source.LocationDataSource
import com.txusmslabs.data.source.RemoteDataSource
import com.txusmslabs.templateapp.framework.data.AndroidPermissionChecker
import com.txusmslabs.templateapp.framework.data.PlayServicesLocationDataSource
import com.txusmslabs.templateapp.framework.data.database.MovieDatabase
import com.txusmslabs.templateapp.framework.data.database.RoomDataSource
import com.txusmslabs.templateapp.framework.data.server.TheMovieDb
import com.txusmslabs.templateapp.framework.data.server.TheMovieDbDataSource
import com.txusmslabs.templateapp.ui.common.SharedViewModel
import com.txusmslabs.templateapp.ui.detail.DetailFragment
import com.txusmslabs.templateapp.ui.detail.DetailViewModel
import com.txusmslabs.templateapp.ui.main.MainFragment
import com.txusmslabs.templateapp.ui.main.MainViewModel
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.GetPopularMovies
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(appModule, dataModule, scopesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { TheMovieDb(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope<MainFragment> {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope<DetailFragment> {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }

    viewModel { SharedViewModel() }
}
