package com.kostyarazboynik.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.usecase.UseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@FeatureScope
class MoviesListFragmentViewModel @Inject constructor(
    useCase: UseCase,
) : ViewModel() {

    val stateFlow: StateFlow<UiState<List<Movie>>> = useCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, UiState.Initial)

//    fun fetchLatest() {
//        val requestResult = repository.fetchLatest()
//    }

    companion object {
        private const val TAG = "MoviesListFragmentViewModel"
    }
}
