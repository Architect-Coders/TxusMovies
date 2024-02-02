package com.txusmslabs.templateapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.ui.common.Event
import com.txusmslabs.templateapp.ui.common.ScopedViewModel
import com.txusmslabs.templateapp.ui.detail.DetailViewState
import com.txusmslabs.usecases.CheckMoviesNewPage
import com.txusmslabs.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMovies: GetPopularMovies,
    private val checkMoviesNewPage: CheckMoviesNewPage,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState: StateFlow<MainViewState> get() = _uiState

    val movies: LiveData<List<Movie>> get() = getPopularMovies.flow().asLiveData()
    private val _navigateToMovie = MutableLiveData<Event<Int>>()
    val navigateToMovie: LiveData<Event<Int>> get() = _navigateToMovie

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>>
        get() {
            if (_requestLocationPermission.value == null) refresh()
            return _requestLocationPermission
        }

    init {
//        initScope()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequested() {
        launch {
            _uiState.value = MainViewState(loading = true)
            notifyLastVisible(0)
//            getPopularMovies.invoke().fold({
//                _uiState.value = MainViewState(loading = false)
//                it
//            }, {
//                _uiState.value = MainViewState(loading = false, movies = it)
//                it
//            })
        }
    }

    fun onMovieClicked(movie: Movie) {
        _navigateToMovie.value = Event(movie.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun notifyLastVisible(lastVisible: Int) {
        launch {
            _uiState.value = MainViewState(loading = true)
            checkMoviesNewPage.invoke(lastVisible)
            _uiState.value = MainViewState(loading = false)
        }
    }
}