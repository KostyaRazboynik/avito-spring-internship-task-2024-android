package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.data.MergeStrategy
import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {

    fun searchMovie(
        movieName: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Movie>>>? = null,
    ): Flow<RequestResult<List<Movie>>>
}