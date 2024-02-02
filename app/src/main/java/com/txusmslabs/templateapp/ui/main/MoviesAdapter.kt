package com.txusmslabs.templateapp.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.ViewMovieBinding
import com.txusmslabs.templateapp.ui.common.basicDiffUtil
import com.txusmslabs.templateapp.ui.common.bindingInflate

class MoviesAdapter(private val listener: (Movie) -> Unit) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(
    DiffCallback) {
//    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    init {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
//    var movies: List<Movie> by basicDiffUtil(
//        emptyList(),
//        areItemsTheSame = { old, new -> old.id == new.id }
//    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_movie, false))

//    override fun getItemCount(): Int = itemCount

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(val dataBinding: ViewMovieBinding) : RecyclerView.ViewHolder(dataBinding.root)

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<Movie>() {


            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ) =
                oldItem == newItem
        }
    }
}