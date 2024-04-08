package com.kostyarazboynik.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.usecase.UseCase
import com.kostyarazboynik.utils.Logger
import com.kostyarazboynik.utils.extensions.launchNamed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@FeatureScope
class MoviesListFragmentViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Initial)
    val uiStateFlow: StateFlow<UiState<List<Movie>>> = _uiStateFlow

    fun loadData() {
        viewModelScope.launchNamed("$TAG-viewModelScope-loadData", Dispatchers.IO) {
            _uiStateFlow.emitAll(useCase())
        }
    }

    fun getCountries(): List<String> {
        val list = mutableSetOf<String>()
        when (val state = _uiStateFlow.value) {
            is UiState.Initial -> Unit
            is UiState.Loading -> state.data?.forEach { movie ->
                list.addAll(movie.countries?.mapNotNull { it.name } ?: listOf())
            }

            is UiState.Success -> state.data.forEach { movie ->
                list.addAll(movie.countries?.mapNotNull { it.name } ?: listOf())
            }

            is UiState.Error -> state.data?.forEach { movie ->
                list.addAll(movie.countries?.mapNotNull { it.name } ?: listOf())
            }
        }
        Logger.d(TAG, list.toString())
        return list.toList()
    }


    companion object {
        private const val TAG = "MoviesListFragmentViewModel"
    }
}
