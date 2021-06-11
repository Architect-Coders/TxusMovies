package com.txusmslabs.templateapp.ui.detail

import com.txusmslabs.templateapp.ui.common.ScopedViewModel
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _uiState = MutableStateFlow(DetailViewState())
    val uiState: StateFlow<DetailViewState> get() = _uiState

    fun findMovie() = launch {
        val m = findMovieById.invoke(movieId)
        if (m != null)
            _uiState.value = DetailViewState(movie = m)
        else
            _uiState.value = DetailViewState(notFound = true)
    }

    fun onFavoriteClicked() = launch {
        launch {
            _uiState.value.movie?.let {
                val updatedMovie = toggleMovieFavorite.invoke(it)
                updatedMovie.let { m ->
                    _uiState.value = DetailViewState(movie = m)
                }
            }
        }
    }
}