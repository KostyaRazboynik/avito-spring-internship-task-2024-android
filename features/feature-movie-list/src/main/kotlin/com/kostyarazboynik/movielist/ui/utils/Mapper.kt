package com.kostyarazboynik.movielist.ui.utils

import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.model.movie.MovieCountry
import com.kostyarazboynik.domain.model.movie.MovieGenre
import kotlinx.coroutines.flow.StateFlow

internal fun getCountries(stateFlow: StateFlow<UiState<List<Movie>>>): List<String> {
    val list = mutableSetOf<String>()
    when (val state = stateFlow.value) {
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
    return list.toList()
}

internal fun getAgeRatings(stateFlow: StateFlow<UiState<List<Movie>>>): List<String> {
    val list = mutableSetOf<String>()
    when (val state = stateFlow.value) {
        is UiState.Initial -> Unit
        is UiState.Loading -> state.data?.forEach { movie ->
            movie.ageRating?.let { list.add(it.toString()) }
        }

        is UiState.Success -> state.data.forEach { movie ->
            movie.ageRating?.let { list.add(it.toString()) }
        }

        is UiState.Error -> state.data?.forEach { movie ->
            movie.ageRating?.let { list.add(it.toString()) }
        }
    }
    return list.toList()
}

internal fun getYears(stateFlow: StateFlow<UiState<List<Movie>>>): List<String> {
    val list = mutableSetOf<String>()
    when (val state = stateFlow.value) {
        is UiState.Initial -> Unit
        is UiState.Loading -> state.data?.forEach { movie ->
            movie.year?.let { list.add(it.toString()) }
        }

        is UiState.Success -> state.data.forEach { movie ->
            movie.year?.let { list.add(it.toString()) }
        }

        is UiState.Error -> state.data?.forEach { movie ->
            movie.year?.let { list.add(it.toString()) }
        }
    }
    return list.toList()
}

internal fun getGenres(stateFlow: StateFlow<UiState<List<Movie>>>): List<String> {
    val list = mutableSetOf<String>()
    when (val state = stateFlow.value) {
        is UiState.Initial -> Unit
        is UiState.Loading -> state.data?.forEach { movie ->
            list.addAll(movie.genres?.mapNotNull { it.name } ?: listOf())
        }

        is UiState.Success -> state.data.forEach { movie ->
            list.addAll(movie.genres?.mapNotNull { it.name } ?: listOf())
        }

        is UiState.Error -> state.data?.forEach { movie ->
            list.addAll(movie.genres?.mapNotNull { it.name } ?: listOf())
        }
    }
    return list.toList()
}

internal fun getMoviesByCountry(movies: List<Movie>, country: String): List<Movie> {
    return movies.filter { movie ->
        val countries = movie.countries
        countries != null && countries.contains(MovieCountry(country))
    }
}

internal fun getMoviesByRating(movies: List<Movie>, rating: String): List<Movie> {
    return movies.filter { movie ->
        val kp = movie.rating?.kp
        kp != null && kp >= rating.toInt()
    }
}

internal fun getMoviesByAgeRating(movies: List<Movie>, ageRating: String): List<Movie> {
    return movies.filter { movie ->
        val rating = movie.ageRating
        rating != null && rating <= ageRating.toInt()
    }
}

internal fun getMoviesByContentType(movies: List<Movie>, contentType: String): List<Movie> {
    return movies.filter { movie ->
        val isSeries = movie.isSeries
        isSeries != null && (contentType == Constants.SERIAL && isSeries || contentType == Constants.MOVIE && !isSeries)
    }
}

internal fun getMoviesByYear(movies: List<Movie>, year: String): List<Movie> {
    return movies.filter { movie ->
        val y = movie.year
        y != null && y == year.toInt()
    }
}

internal fun getMoviesByGenre(movies: List<Movie>, genre: String): List<Movie> {
    return movies.filter { movie ->
        val genres = movie.genres
        genres != null && genres.contains(MovieGenre(genre))
    }
}


