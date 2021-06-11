package com.txusmslabs.templateapp.ui.detail

import com.txusmslabs.domain.Movie

data class DetailViewState(val movie: Movie? = null, val notFound: Boolean = false)