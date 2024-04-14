package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.GetMovieDetailsRepository
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDetailedDto
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedata.utils.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetMovieDetailsRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
) : GetMovieDetailsRepository {

    override fun getMovieDetails(id: Int): Flow<RequestResult<Movie>> =
        getDetailedMovieFromServer(id)

    private fun getDetailedMovieFromServer(id: Int): Flow<RequestResult<Movie>> {
        val apiRequest: Flow<RequestResult<MovieDetailedDto>> =
            flow { emit(api.loadMovieDetail(id)) }
                .map { result -> result.toRequestResult() }

        val start = flowOf<RequestResult<MovieDetailedDto>>(RequestResult.InProgress())

        return merge(apiRequest, start).map { result ->
            result.map {
                it.toMovie().apply {
                    this.persons?.filter { person ->
                        val profession = person.enProfession
                        profession != null && profession == "actor"
                    }
                }
            }
        }
    }

    private companion object {
        private const val TAG = "GetMovieDetailsRepositoryImpl"
    }
}
