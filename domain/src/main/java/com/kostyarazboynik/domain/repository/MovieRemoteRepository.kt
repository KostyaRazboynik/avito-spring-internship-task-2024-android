package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.model.DataState
import com.kostyarazboynik.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteRepository {
    suspend fun getAll(): Flow<DataState<List<Movie>>>
}