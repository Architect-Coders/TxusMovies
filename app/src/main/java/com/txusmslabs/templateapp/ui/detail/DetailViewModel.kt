package com.txusmslabs.templateapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.ui.common.ScopedViewModel
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() {
            if (_movie.value == null && _notFound.value == null) findMovie()
            return _movie
        }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    private val _notFound = MutableLiveData<Boolean>()
    val notFound: LiveData<Boolean> get() = _notFound

    private fun findMovie() = launch {
        _movie.value = findMovieById.invoke(movieId)
        updateUi()
    }

    fun onFavoriteClicked() = launch {
        launch {
            movie.value?.let {
                val updatedMovie = toggleMovieFavorite.invoke(it)
                updatedMovie.let { m ->
                    _movie.value = m
                    updateUi()
                }
            }
        }
    }

    private fun updateUi() {
        movie.value?.run {
            _title.value = title
            _overview.value = overview
            _url.value = backdropPath
            _favorite.value = favorite
        } ?: kotlin.run { _notFound.postValue(true) }
    }
}