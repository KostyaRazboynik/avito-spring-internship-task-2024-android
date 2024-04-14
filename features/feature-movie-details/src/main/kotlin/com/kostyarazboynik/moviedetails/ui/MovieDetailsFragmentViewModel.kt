package com.kostyarazboynik.moviedetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.domain.connection.ConnectivityObserver
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.model.movie.MovieReview
import com.kostyarazboynik.domain.usecase.GetMoviePostersUseCase
import com.kostyarazboynik.domain.usecase.GetMovieDetailsUseCase
import com.kostyarazboynik.domain.usecase.GetMovieReviewsUseCase
import com.kostyarazboynik.utils.Logger
import com.kostyarazboynik.utils.extensions.launchNamed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

@FeatureScope
class MovieDetailsFragmentViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMoviePostersUseCase: GetMoviePostersUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<Movie>> = MutableStateFlow(UiState.Initial)
    val uiStateFlow: StateFlow<UiState<Movie>> = _uiStateFlow

    private val _postersStateFlow: MutableStateFlow<UiState<List<MoviePoster>>> =
        MutableStateFlow(UiState.Initial)
    val postersStateFlow: StateFlow<UiState<List<MoviePoster>>> = _postersStateFlow

    private val _reviewStateFlow: MutableStateFlow<UiState<List<MovieReview>>> =
        MutableStateFlow(UiState.Initial)
    val reviewStateFlow: StateFlow<UiState<List<MovieReview>>> = _reviewStateFlow

    private val _internetStatus =
        MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val internetStatus: StateFlow<ConnectivityObserver.Status> = _internetStatus

    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launchNamed("$TAG-viewModelScope-observeNetwork", Dispatchers.IO) {
            connectivityObserver.observe().collect {
                _internetStatus.emit(it)
            }
        }
    }

    fun loadMovie(movie: Movie) {
        viewModelScope.launchNamed("$TAG-viewModelScope-loadMovie", Dispatchers.IO) {
            _uiStateFlow.emit(UiState.Success(movie))
            Logger.d(TAG, "movie=$movie")
            _uiStateFlow.emitAll(getMovieDetailsUseCase(movie.id))
        }
    }

    fun loadMoviePosters(movie: Movie) {
        if (internetStatus.value == ConnectivityObserver.Status.Available) {
            viewModelScope.launchNamed("$TAG-viewModelScope-loadMoviePosters", Dispatchers.IO) {
                _postersStateFlow.emitAll(getMoviePostersUseCase(movie.id))
            }
        }
    }

    fun loadMovieReviews(movie: Movie) {
        if (internetStatus.value == ConnectivityObserver.Status.Available) {
            viewModelScope.launchNamed("$TAG-viewModelScope-loadMovieReviews", Dispatchers.IO) {
                _reviewStateFlow.emitAll(getMovieReviewsUseCase(movie.id))
            }
        }
    }

    companion object {
        private const val TAG = "MovieDetailsFragmentViewModel"
    }
}
