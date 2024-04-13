package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {

    fun searchMovie(movieName: String): Flow<RequestResult<List<Movie>>>
}
