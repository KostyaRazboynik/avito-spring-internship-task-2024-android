package com.kostyarazboynik.domain.model

sealed class UiState<out T> {
    data object Initial : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Loading<T>(val data: T? = null) : UiState<T>()
    data class Error<T>(val data: T? = null, val cause: String? = null) : UiState<T>()
}
