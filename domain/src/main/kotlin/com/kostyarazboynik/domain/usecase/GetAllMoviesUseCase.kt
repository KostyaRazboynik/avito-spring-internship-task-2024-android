package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.domain.mapper.toUiState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: GetAllMoviesRepository
) {

    operator fun invoke(page: Int): Flow<UiState<List<Movie>>> = repository.getAllMovies(page).map { it.toUiState() }
}
