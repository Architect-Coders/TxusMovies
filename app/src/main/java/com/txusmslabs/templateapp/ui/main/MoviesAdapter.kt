package com.txusmslabs.templateapp.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.ViewMovieBinding
import com.txusmslabs.templateapp.framework.data.database.Movie
import com.txusmslabs.templateapp.ui.common.basicDiffUtil
import com.txusmslabs.templateapp.ui.common.bindingInflate

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_movie, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(val dataBinding: ViewMovieBinding) : RecyclerView.ViewHolder(dataBinding.root)
}