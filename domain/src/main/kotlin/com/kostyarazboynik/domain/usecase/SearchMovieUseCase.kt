package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.domain.mapper.toUiState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: SearchMovieRepository
) {
    operator fun invoke(movieName: String): Flow<UiState<List<Movie>>> =
        repository.searchMovie(movieName).map { it.toUiState() }
}