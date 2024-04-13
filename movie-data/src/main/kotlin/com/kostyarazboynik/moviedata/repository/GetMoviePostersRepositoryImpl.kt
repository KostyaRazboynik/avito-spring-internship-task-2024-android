package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.repository.GetMoviePostersRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.response.PosterListResponse
import com.kostyarazboynik.moviedata.mapper.toMoviePoster
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMoviePostersRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
) : GetMoviePostersRepository {

    override fun getPosters(movieId: Int): Flow<RequestResult<List<MoviePoster>>> =
        getAllFromServer(movieId)

    private fun getAllFromServer(movieId: Int): Flow<RequestResult<List<MoviePoster>>> {
        val apiRequest: Flow<RequestResult<PosterListResponse>> =
            flow { emit(api.loadMoviePosters(movieId = movieId)) }
                .map { result -> result.toRequestResult() }

        val start = flowOf<RequestResult<PosterListResponse>>(RequestResult.InProgress())

        return merge(apiRequest, start)
            .map { result: RequestResult<PosterListResponse> ->
                result.map { response: PosterListResponse ->
                    response.postersList.map { it.toMoviePoster() }
                }
            }
    }

    private companion object {
        private const val TAG = "GetAllMoviePostersRepositoryImpl"
    }
}
