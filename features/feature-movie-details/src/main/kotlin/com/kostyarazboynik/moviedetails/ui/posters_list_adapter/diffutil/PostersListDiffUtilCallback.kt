package com.kostyarazboynik.moviedetails.ui.posters_list_adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kostyarazboynik.domain.model.movie.MoviePoster

class PostersListDiffUtilCallback : DiffUtil.ItemCallback<MoviePoster>() {

    override fun areItemsTheSame(
        oldItem: MoviePoster,
        newItem: MoviePoster
    ): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(
        oldItem: MoviePoster,
        newItem: MoviePoster
    ): Boolean =
        oldItem == newItem
}
