package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.data.MergeStrategy
import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface GetAllMoviesRepository {
    fun getAllMovies(
        mergeStrategy: MergeStrategy<RequestResult<List<Movie>>>? = null,
    ): Flow<RequestResult<List<Movie>>>

    fun fetchLatest(): Flow<RequestResult<List<Movie>>>
}