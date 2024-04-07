package com.kostyarazboynik.domain.usecase

import com.kostyarazboynik.utils.Logger
import com.kostyarazboynik.domain.model.DataState
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.repository.MovieRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCase @Inject constructor(
    private val repository: MovieRemoteRepository
) {

    suspend fun getMovies(): Flow<UiState<List<Movie>>> = flow {
        Logger.d("UseCase", "getMovies")
        repository.getAll().collect { state ->
            when (state) {
                DataState.Initial -> emit(UiState.Initial)
                is DataState.Exception -> emit(
                    UiState.Error(
                        state.cause.message.toString(),
                        state.code
                    )
                )

                is DataState.Result -> {
                    emit(UiState.Success(state.data))
                }
            }
        }
    }
}