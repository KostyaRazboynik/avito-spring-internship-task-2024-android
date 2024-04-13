package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.MovieReview
import com.kostyarazboynik.domain.repository.GetMovieReviewsRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.response.ReviewListResponse
import com.kostyarazboynik.moviedata.mapper.toMovieReview
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.toRequestResult
import com.kostyarazboynik.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieReviewsRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
) : GetMovieReviewsRepository {

    override fun getReviews(movieId: Int): Flow<RequestResult<List<MovieReview>>> =
        getAllFromServer(movieId)

    private fun getAllFromServer(movieId: Int): Flow<RequestResult<List<MovieReview>>> {
        val apiRequest: Flow<RequestResult<ReviewListResponse>> =
            flow { emit(api.loadMovieReviews(movieId = movieId)) }
                .map { result ->
                    Logger.d(TAG, "$result")
                    result.toRequestResult() }

        val start = flowOf<RequestResult<ReviewListResponse>>(RequestResult.InProgress())

        return merge(apiRequest, start)
            .map { result: RequestResult<ReviewListResponse> ->
                result.map { response: ReviewListResponse ->
                    response.reviewList.map { it.toMovieReview() }
                }
            }
    }

    private companion object {
        private const val TAG = "GetMovieReviewsRepositoryImpl"
    }
}
