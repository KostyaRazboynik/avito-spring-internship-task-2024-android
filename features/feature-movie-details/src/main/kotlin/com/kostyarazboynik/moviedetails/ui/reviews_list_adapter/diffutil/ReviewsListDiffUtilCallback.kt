package com.kostyarazboynik.moviedetails.ui.reviews_list_adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.model.movie.MovieReview

class ReviewsListDiffUtilCallback : DiffUtil.ItemCallback<MovieReview>() {

    override fun areItemsTheSame(
        oldItem: MovieReview,
        newItem: MovieReview
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieReview,
        newItem: MovieReview
    ): Boolean =
        oldItem == newItem
}
