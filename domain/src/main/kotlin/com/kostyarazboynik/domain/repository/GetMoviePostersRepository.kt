package com.kostyarazboynik.domain.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.MoviePoster
import kotlinx.coroutines.flow.Flow

interface GetMoviePostersRepository {

    fun getPosters(movieId: Int): Flow<RequestResult<List<MoviePoster>>>
}
