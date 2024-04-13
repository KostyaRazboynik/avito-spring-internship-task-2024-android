package com.kostyarazboynik.moviedetails.ui.reviews_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostyarazboynik.domain.model.movie.MovieReview
import com.kostyarazboynik.feature_movie_details.databinding.PostersListItemLayoutBinding
import com.kostyarazboynik.feature_movie_details.databinding.ReviewsListItemLayoutBinding
import com.kostyarazboynik.moviedetails.ui.reviews_list_adapter.diffutil.ReviewsListDiffUtilCallback

class ReviewsListAdapter :
    ListAdapter<MovieReview, ReviewsListViewHolder>(ReviewsListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsListViewHolder =
        ReviewsListViewHolder(
            ReviewsListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: ReviewsListViewHolder, position: Int) {
        val poster = getItem(position)
        holder.bind(poster)
    }

    private companion object {
        private const val TAG = "PostersListAdapter"
    }
}
