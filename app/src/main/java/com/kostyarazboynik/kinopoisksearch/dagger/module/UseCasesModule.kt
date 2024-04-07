package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.MovieRemoteRepository
import com.kostyarazboynik.domain.usecase.UseCase
import com.kostyarazboynik.movielist.ui.MoviesListFragmentViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UseCasesModule {

    @Provides
    fun provideUseCase(movieRemoteRepository: MovieRemoteRepository): UseCase =
        UseCase(movieRemoteRepository)
}
