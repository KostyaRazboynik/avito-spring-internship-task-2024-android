package com.kostyarazboynik.domain.mapper

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie

internal fun RequestResult<List<Movie>>.toUiState(): UiState<List<Movie>> {
    return when (this) {
        is RequestResult.Success -> UiState.Success(data)
        is RequestResult.InProgress -> UiState.Loading(data)
        is RequestResult.Error -> UiState.Error(data, error?.message)
    }
}