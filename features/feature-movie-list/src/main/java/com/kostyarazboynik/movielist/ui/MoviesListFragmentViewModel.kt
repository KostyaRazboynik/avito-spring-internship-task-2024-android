package com.kostyarazboynik.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.utils.Logger
import com.kostyarazboynik.utils.extensions.launchNamed
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

@FeatureScope
class MoviesListFragmentViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _companiesListFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Initial)
    val companiesListFlow: StateFlow<UiState<List<Movie>>> = _companiesListFlow

    fun loadRemoteData() {
        Logger.d(TAG, "loadRemoteData")
        viewModelScope.launchNamed("$TAG-viewModelScope-loadRemoteData", Dispatchers.IO) {
            _companiesListFlow.emitAll(
                useCase.getMovies()
            )
        }
    }

    companion object {
        private const val TAG = "MoviesListFragmentViewModel"
    }
}
