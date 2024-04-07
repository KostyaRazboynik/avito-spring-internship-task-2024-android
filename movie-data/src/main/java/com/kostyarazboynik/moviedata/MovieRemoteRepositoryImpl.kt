package com.kostyarazboynik.moviedata

import com.kostyarazboynik.domain.model.DataState
import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.moviedata.mapper.toMovie
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.MovieRemoteRepository
import com.kostyarazboynik.utils.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MovieRemoteRepositoryImpl @Inject constructor(
    private val api: MoviesApi
): MovieRemoteRepository {

    override suspend fun getAll(): Flow<DataState<List<Movie>>> = flow {
        try {
            Logger.d(TAG, "getAll")
            val networkListResponse = api.loadMovies()
            if (networkListResponse.isSuccessful) {
                networkListResponse.body()?.let { responseList ->
                    emit(DataState.Result(responseList.movieList.mapNotNull { movieDto ->
                        Logger.d(TAG, "id=${movieDto.id}")
                        api.loadMovieDetail(
                            movieDto.id
                        ).body()?.toMovie()
                    }))
                }
            } else {
                val exception = HttpException(networkListResponse)
                Logger.d(TAG, "else brunch, ")
                emit(DataState.Exception(exception, exception.code()))
                networkListResponse.errorBody()?.close()
            }
        } catch (exception: HttpException) {
            Logger.d(TAG, "HttpException: $exception, ")
            emit(DataState.Exception(exception, exception.code()))
        } catch (exception: Exception) {
            Logger.d(TAG, "exception: $exception, ")

            emit(DataState.Exception(exception, UNKNOWN_EXCEPTION_CODE))
        }
    }

    private companion object {
        private const val UNKNOWN_EXCEPTION_CODE = -1
        private const val TAG = "MovieRemoteRepository"
    }
}
