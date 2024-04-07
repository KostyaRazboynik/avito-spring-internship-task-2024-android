package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.MovieRemoteRepository
import com.kostyarazboynik.moviedata.MovieRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {

    @Reusable
    @Binds
    fun bindMoviesRemoteRepository(
        moviesRemoteRepository: MovieRemoteRepositoryImpl,
    ): MovieRemoteRepository
}
