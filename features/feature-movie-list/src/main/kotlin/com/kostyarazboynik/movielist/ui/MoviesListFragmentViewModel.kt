package com.kostyarazboynik.movielist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.usecase.UseCase
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
import kotlinx.coroutines.flow.emitAll
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

    fun filterMovies(filteringOption: String, option: String): List<Movie> {
        return when (val state = uiStateFlow.value) {
            is UiState.Initial -> listOf()
            is UiState.Loading -> {
                if (state.data == null) listOf()
                else filterMovies(filteringOption, option, state.data!!)
            }

            is UiState.Success -> filterMovies(filteringOption, option, state.data)
            is UiState.Error -> {
                if (state.data == null) listOf()
                else filterMovies(filteringOption, option, state.data!!)
            }
        }
    }

    private fun filterMovies(
        filteringOption: String,
        option: String,
        movies: List<Movie>
    ): List<Movie> {
        return when (filteringOption) {
            Constants.AGE_RATING -> getMoviesByAgeRating(movies, option)
            Constants.RATING -> getMoviesByRating(movies, option)
            Constants.COUNTRY -> getMoviesByCountry(movies, option)
            Constants.CONTENT_TYPE -> getMoviesByContentType(movies, option)
            Constants.YEAR -> getMoviesByYear(movies, option)
            Constants.GENRE -> getMoviesByGenre(movies, option)
            else -> error("Unreachable brunch")
        }
    }

    companion object {
        private const val TAG = "MoviesListFragmentViewModel"
    }
}
