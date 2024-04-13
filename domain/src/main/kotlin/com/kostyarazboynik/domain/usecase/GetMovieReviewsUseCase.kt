package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.domain.mapper.toUiState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.MovieReview
import com.kostyarazboynik.domain.repository.GetMovieReviewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: GetMovieReviewsRepository
) {

    operator fun invoke(movieId: Int): Flow<UiState<List<MovieReview>>> =
        repository.getReviews(movieId).map { it.toUiState() }
}
