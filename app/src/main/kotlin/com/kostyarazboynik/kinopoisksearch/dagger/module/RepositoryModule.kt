package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.moviedata.repository.GetAllLocalMoviesRepositoryImpl
import com.kostyarazboynik.moviedata.repository.GetAllMoviesRepositoryImpl
import com.kostyarazboynik.moviedata.repository.SearchMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {

    @Reusable
    @Binds
    fun bindGetAllMoviesRepository(
        getAllMoviesRepository: GetAllMoviesRepositoryImpl,
    ): GetAllMoviesRepository

    @Reusable
    @Binds
    fun bindSearchMovieRepositoryImpl(
        searchMovieRepository: SearchMovieRepositoryImpl,
    ): SearchMovieRepository

    @Reusable
    @Binds
    fun bindGetAllLocalMoviesRepository(
        getAllLocalMoviesRepository: GetAllLocalMoviesRepositoryImpl,
    ): GetAllLocalMoviesRepository
}
