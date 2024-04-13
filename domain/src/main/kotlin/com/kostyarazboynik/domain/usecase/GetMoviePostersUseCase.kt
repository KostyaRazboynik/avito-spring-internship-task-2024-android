package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.domain.mapper.toUiState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.repository.GetMoviePostersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviePostersUseCase @Inject constructor(
    private val repository: GetMoviePostersRepository
) {

    operator fun invoke(movieId: Int): Flow<UiState<List<MoviePoster>>> =
        repository.getPosters(movieId).map { it.toUiState() }
}