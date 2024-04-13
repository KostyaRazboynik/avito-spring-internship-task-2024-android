package com.kostyarazboynik.moviedetails.ui.actors_list_adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kostyarazboynik.domain.model.movie.MoviePerson

class ActorsListDiffUtilCallback : DiffUtil.ItemCallback<MoviePerson>() {

    override fun areItemsTheSame(
        oldItem: MoviePerson,
        newItem: MoviePerson
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MoviePerson,
        newItem: MoviePerson
    ): Boolean =
        oldItem == newItem
}
