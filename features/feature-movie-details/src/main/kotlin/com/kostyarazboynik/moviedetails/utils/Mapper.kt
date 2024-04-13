package com.kostyarazboynik.moviedetails.utils

import com.kostyarazboynik.domain.model.UiState

internal fun <T : Any> UiState<List<T>>.getList(): List<T> {
    return when (this) {
        is UiState.Initial -> listOf()
        is UiState.Loading -> this.data ?: listOf()
        is UiState.Success -> this.data
        is UiState.Error -> this.data ?: listOf()
    }
}