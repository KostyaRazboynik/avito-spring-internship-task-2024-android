package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.response.MovieListResponse
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.search
import com.kostyarazboynik.moviedata.utils.toRequestResult
import com.kostyarazboynik.moviedatabase.MovieDatabase
import com.kostyarazboynik.moviedatabase.model.MovieDbo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase,
) : SearchMovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchMovie(
        movieName: String,
    ): Flow<RequestResult<List<Movie>>> {

        return searchFromServer(movieName)
            .flatMapConcat { result ->
                if (result is RequestResult.Error) {
                    searchFromDatabase(movieName)
                } else {
                    flowOf(result)
                }
            }
    }

    private fun searchFromServer(movieName: String): Flow<RequestResult<List<Movie>>> {
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

    private fun searchFromDatabase(movieName: String): Flow<RequestResult<List<Movie>>> {
        val dbRequest: Flow<RequestResult<List<MovieDbo>>> =
            database.moviesDbo::getAllMovies.asFlow()
                .map { RequestResult.Success(it) }

        val start = flowOf<RequestResult<List<MovieDbo>>>(RequestResult.InProgress())

        return merge(dbRequest, start)
            .map { result: RequestResult<List<MovieDbo>> ->
                result.map { movieDbos -> movieDbos.map { it.toMovie() }.search(movieName) }
            }
    }

    private companion object {
        private const val TAG = "SearchMovieRepositoryImpl"
    }
}
