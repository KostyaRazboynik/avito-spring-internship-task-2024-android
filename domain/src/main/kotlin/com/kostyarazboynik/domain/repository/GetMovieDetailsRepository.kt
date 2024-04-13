package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsRepository {

    fun getMovieDetails(id: Int): Flow<RequestResult<Movie>>
}
