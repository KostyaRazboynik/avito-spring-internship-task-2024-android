package com.kostyarazboynik.moviedata.repository

import com.kostyarazboynik.domain.model.RequestResult
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.moviedata.utils.map
import com.kostyarazboynik.moviedatabase.MovieDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllLocalMoviesRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
) : GetAllLocalMoviesRepository {

    override fun getAllLocalMovies(): Flow<RequestResult<List<Movie>>> = getAllFromDataBase()

    private fun getAllFromDataBase(): Flow<RequestResult<List<Movie>>> {
        return database.moviesDbo::getAllMovies.asFlow()
            .map { RequestResult.Success(it) }
            .map { result -> result.map { movieDbos -> movieDbos.map { it.toMovie() } } }
    }

    private companion object {
        private const val TAG = "GetAllLocalMoviesRepositoryImpl"
    }
}
