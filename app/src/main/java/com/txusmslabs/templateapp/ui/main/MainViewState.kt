package com.txusmslabs.templateapp.ui.main

import com.txusmslabs.domain.Movie

data class MainViewState(val loading: Boolean = false, val movies: List<Movie> = emptyList())