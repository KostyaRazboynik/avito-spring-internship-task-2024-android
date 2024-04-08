package com.kostyarazboynik.movielist.ui.list_adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kostyarazboynik.domain.model.movie.Movie

class MoviesListDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean =
        oldItem == newItem
}
