package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.data.MergeStrategy
import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.response.MovieListResponse
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.moviedata.mapper.toMovieDbo
import com.kostyarazboynik.moviedata.merge_strategy.RequestResultMergeStrategy
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.toRequestResult
import com.kostyarazboynik.moviedatabase.MovieDatabase
import com.kostyarazboynik.moviedatabase.model.MovieDbo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetAllMoviesRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase,
) : GetAllMoviesRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllMovies(
        page: Int,
        mergeStrategy: MergeStrategy<RequestResult<List<Movie>>>?,
    ): Flow<RequestResult<List<Movie>>> {
        val cachedMoviesFlow: Flow<RequestResult<List<Movie>>> = getAllFromDatabase()
        val remoteMoviesFlow: Flow<RequestResult<List<Movie>>> = getAllFromServer(page)
        val mergeStrategyChecked = mergeStrategy ?: RequestResultMergeStrategy<List<Movie>>()

        return cachedMoviesFlow.combine(remoteMoviesFlow, mergeStrategyChecked::merge)
            .flatMapConcat { result ->
                flowOf(when (result) {

                    is RequestResult.Success -> RequestResult.Success(mergeLists(
                        result.data,
                        database.moviesDbo.getAllMovies().map { it.toMovie() }
                    ))

                    is RequestResult.Error -> RequestResult.Error(mergeLists(
                        result.data ?: listOf(),
                        database.moviesDbo.getAllMovies().map { it.toMovie() }
                    ))

                    is RequestResult.InProgress -> RequestResult.InProgress(mergeLists(
                        result.data ?: listOf(),
                        database.moviesDbo.getAllMovies().map { it.toMovie() }
                    ))
                })
            }
    }

    private fun getAllFromDatabase(): Flow<RequestResult<List<Movie>>> {
        val dbRequest: Flow<RequestResult<List<MovieDbo>>> =
            database.moviesDbo::getAllMovies.asFlow()
                .map { RequestResult.Success(it) }

        val start = flowOf<RequestResult<List<MovieDbo>>>(RequestResult.InProgress())

        return merge(dbRequest, start)
            .map { result -> result.map { movieDbos -> movieDbos.map { it.toMovie() } } }
    }

    private fun getAllFromServer(page: Int): Flow<RequestResult<List<Movie>>> {
        val apiRequest: Flow<RequestResult<MovieListResponse>> =
            flow { emit(api.loadMovies(page = page)) }
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

    private suspend fun saveNetworkResponseToCache(data: List<Movie>) {
        database.moviesDbo.insert(data.map { it.toMovieDbo() })
    }

    private suspend fun mergeLists(
        mergedList: List<Movie>,
        localList: List<Movie>,
    ): List<Movie> {
        return localList.toMutableList().apply {
            val newMovies = mergedList.filter { movieMerged ->
                movieMerged.id !in localList.map { it.id }.toSet()
            }
            addAll(newMovies)
            saveNetworkResponseToCache(newMovies)
        }
    }

    private companion object {
        private const val TAG = "GetAllMoviesRepositoryImpl"
    }
}
