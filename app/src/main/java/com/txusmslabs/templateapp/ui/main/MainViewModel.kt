package com.txusmslabs.templateapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.ui.common.Event
import com.txusmslabs.templateapp.ui.common.ScopedViewModel
import com.txusmslabs.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMovies: GetPopularMovies,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToMovie = MutableLiveData<Event<Int>>()
    val navigateToMovie: LiveData<Event<Int>> get() = _navigateToMovie

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>>
        get() {
            if (_requestLocationPermission.value == null) refresh()
            return _requestLocationPermission
        }

    init {
        initScope()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _movies.value = getPopularMovies.invoke()
            _loading.value = false
        }
    }

    fun onMovieClicked(movie: Movie) {
        _navigateToMovie.value = Event(movie.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}