package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.model.movie.MovieReview
import kotlinx.coroutines.flow.Flow

interface GetMovieReviewsRepository {

    fun getReviews(movieId: Int): Flow<RequestResult<List<MovieReview>>>
}
