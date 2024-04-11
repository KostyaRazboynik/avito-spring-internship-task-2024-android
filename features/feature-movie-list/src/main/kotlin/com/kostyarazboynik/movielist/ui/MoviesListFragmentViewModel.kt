package com.kostyarazboynik.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.domain.connection.ConnectivityObserver
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.usecase.GetAllLocalMoviesUseCase
import com.kostyarazboynik.domain.usecase.GetAllMoviesUseCase
import com.kostyarazboynik.domain.usecase.SearchMovieUseCase
import com.kostyarazboynik.movielist.ui.utils.Constants
import com.kostyarazboynik.movielist.ui.utils.getAgeRatings
import com.kostyarazboynik.movielist.ui.utils.getCountries
import com.kostyarazboynik.movielist.ui.utils.getGenres
import com.kostyarazboynik.movielist.ui.utils.getMoviesByAgeRating
import com.kostyarazboynik.movielist.ui.utils.getMoviesByContentType
import com.kostyarazboynik.movielist.ui.utils.getMoviesByCountry
import com.kostyarazboynik.movielist.ui.utils.getMoviesByGenre
import com.kostyarazboynik.movielist.ui.utils.getMoviesByRating
import com.kostyarazboynik.movielist.ui.utils.getMoviesByYear
import com.kostyarazboynik.movielist.ui.utils.getYears
import com.kostyarazboynik.utils.extensions.launchNamed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@FeatureScope
class MoviesListFragmentViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getAllLocalMoviesUseCase: GetAllLocalMoviesUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Initial)
    val uiStateFlow: StateFlow<UiState<List<Movie>>> = _uiStateFlow
    private val internetStatus =
        MutableStateFlow(ConnectivityObserver.Status.Available)

    private var alreadyLoadedLocalData = false

    init {
        observeNetwork()
    }

    private fun observeNetwork() =
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest {
                internetStatus.emit(it)
            }
        }

    fun loadAllData() {
        if (internetStatus.value == ConnectivityObserver.Status.Available) {
            loadAllDataForced()
        } else {
            loadLocalData()
        }
    }

    fun loadLocalData() {
        viewModelScope.launchNamed("$TAG-viewModelScope-loadData", Dispatchers.IO) {
            val data = getAllLocalMoviesUseCase()
            data.collectLatest {
                if (getUiStateDataSize(it) > 0) {
                    _uiStateFlow.emitAll(data)
                } else {
                    loadAllDataForced()
                }
            }
        }
    }

    fun loadAllDataForced() {
        viewModelScope.launchNamed("$TAG-viewModelScope-loadData", Dispatchers.IO) {
            _uiStateFlow.emitAll(getAllMoviesUseCase(countPage()))
        }
        alreadyLoadedLocalData = false
    }

    fun searchMovie(movieName: String) {
        viewModelScope.launchNamed("$TAG-viewModelScope-loadData", Dispatchers.IO) {
            _uiStateFlow.emitAll(searchMovieUseCase(movieName))
        }
        alreadyLoadedLocalData = false
    }

    fun getOption(item: String): List<String> {
        return when (item) {
            Constants.AGE_RATING -> getAgeRatings(_uiStateFlow)
            Constants.RATING -> Constants.numberList
            Constants.COUNTRY -> getCountries(_uiStateFlow)
            Constants.CONTENT_TYPE -> listOf(Constants.SERIAL, Constants.MOVIE)
            Constants.YEAR -> getYears(_uiStateFlow)
            Constants.GENRE -> getGenres(_uiStateFlow)
            else -> error("Unreachable brunch")
        }
    }

    fun filterMovies(
        filteringOption: String,
        optionType: String,
        movies: List<Movie>
    ): List<Movie> {
        return when (filteringOption) {
            Constants.AGE_RATING -> getMoviesByAgeRating(movies, optionType)
            Constants.RATING -> getMoviesByRating(movies, optionType)
            Constants.COUNTRY -> getMoviesByCountry(movies, optionType)
            Constants.CONTENT_TYPE -> getMoviesByContentType(movies, optionType)
            Constants.YEAR -> getMoviesByYear(movies, optionType)
            Constants.GENRE -> getMoviesByGenre(movies, optionType)
            else -> error("Unreachable brunch")
        }
    }

    private fun getUiStateDataSize(uiState: UiState<List<Movie>>): Int {
        return when (uiState) {
            UiState.Initial -> 0
            is UiState.Loading -> uiState.data?.size ?: 0
            is UiState.Success -> uiState.data.size
            is UiState.Error -> uiState.data?.size ?: 0
        }
    }

    private fun countPage(): Int {
        return when (val uiState = _uiStateFlow.value) {
            UiState.Initial -> 0
            is UiState.Loading -> (uiState.data?.size ?: 0) / PAGE_SIZE
            is UiState.Success -> uiState.data.size / PAGE_SIZE
            is UiState.Error -> (uiState.data?.size ?: 0) / PAGE_SIZE
        } + 1
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val TAG = "MoviesListFragmentViewModel"
    }
}
