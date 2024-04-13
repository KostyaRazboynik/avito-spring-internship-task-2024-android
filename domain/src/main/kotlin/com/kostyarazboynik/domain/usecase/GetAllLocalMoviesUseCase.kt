package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.domain.mapper.toUiState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllLocalMoviesUseCase @Inject constructor(
    private val repository: GetAllLocalMoviesRepository
) {

    operator fun invoke(): Flow<UiState<List<Movie>>> =
        repository.getAllLocalMovies().map { it.toUiState() }
}
