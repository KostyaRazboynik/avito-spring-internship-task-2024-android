package com.kostyarazboynik.moviedetails.ui.reviews_list_adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kostyarazboynik.domain.model.movie.MovieReview
import com.kostyarazboynik.feature_movie_details.R
import com.kostyarazboynik.feature_movie_details.databinding.ReviewsListItemLayoutBinding

class ReviewsListViewHolder(
    private val binding: ReviewsListItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(review: MovieReview) {
        binding.apply {
            review.author?.let {
                reviewAuthor.text = it
                reviewAuthor.isVisible = true
            }
            review.title?.let {
                reviewTitle.text = it
                reviewTitle.isVisible = true
            }
            review.review?.let {
                reviewText.text = it
                reviewText.isVisible = true
            }
            review.type?.let {
                reviewRating.isVisible = true
                when(it) {
                    POSITIVE_REVIEW_TYPE -> reviewRating.setBackgroundResource(R.color.green)
                    NEGATIVE_REVIEW_TYPE -> reviewRating.setBackgroundResource(R.color.red)
                    else -> reviewRating.isVisible = false
                }
            }
        }
    }

    private companion object {
        private const val POSITIVE_REVIEW_TYPE = "Позитивный"
        private const val NEGATIVE_REVIEW_TYPE = "Негативный"
    }
}
