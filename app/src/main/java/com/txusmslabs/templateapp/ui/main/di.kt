package com.txusmslabs.templateapp.ui.main

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MainViewModel(getPopularMovies)
    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent (modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModel: MainViewModel
}