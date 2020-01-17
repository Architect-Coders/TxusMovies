package com.txusmslabs.templateapp.ui.detail

import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val movieId: Int) {

    @Provides
    fun detailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ): DetailViewModel {
        return DetailViewModel(movieId, findMovieById, toggleMovieFavorite)
    }

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModel: DetailViewModel
}