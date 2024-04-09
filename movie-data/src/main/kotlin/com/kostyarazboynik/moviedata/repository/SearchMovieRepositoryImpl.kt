package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.data.MergeStrategy
import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.response.MovieListResponse
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
) : SearchMovieRepository {

    override fun searchMovie(
        movieName: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Movie>>>?
    ): Flow<RequestResult<List<Movie>>> = getSearchFromServer(movieName)

    private fun getSearchFromServer(movieName: String): Flow<RequestResult<List<Movie>>> {
        val apiRequest: Flow<RequestResult<MovieListResponse>> =
            flow { emit(api.searchMovie(movieName)) }
                .map { result -> result.toRequestResult() }

        val start = flowOf<RequestResult<MovieListResponse>>(RequestResult.InProgress())

        return merge(apiRequest, start)
            .map { result: RequestResult<MovieListResponse> ->
                result.map { response: MovieListResponse ->
                    response.movieList
                        .map { it.toMovie() }
                        .filter { movie ->
                            val rating = movie.rating?.kp
                            rating != null && rating > 0
                        }
                }
            }
    }

    private companion object {
        private const val TAG = "SearchMovieRepositoryImpl"
    }
}
