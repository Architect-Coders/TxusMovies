package com.txusmslabs.templateapp.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.txusmslabs.templateapp.framework.data.database.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    (adapter as? MoviesAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}