package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import com.kostyarazboynik.domain.repository.GetMovieDetailsRepository
import com.kostyarazboynik.domain.repository.GetMoviePostersRepository
import com.kostyarazboynik.domain.repository.GetMovieReviewsRepository
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.moviedata.repository.GetAllLocalMoviesRepositoryImpl
import com.kostyarazboynik.moviedata.repository.GetAllMoviesRepositoryImpl
import com.kostyarazboynik.moviedata.repository.GetMovieDetailsRepositoryImpl
import com.kostyarazboynik.moviedata.repository.GetMoviePostersRepositoryImpl
import com.kostyarazboynik.moviedata.repository.GetMovieReviewsRepositoryImpl
import com.kostyarazboynik.moviedata.repository.SearchMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
[a-z]+((\.[a-z][A-Za-z0-9]*)+(_)+(\.[a-z][A-Za-z0-9])*)*
@Module
interface RepositoryModule {

    @Reusable
    @Binds
    fun bindGetAllMoviesRepository(
        getAllMoviesRepository: GetAllMoviesRepositoryImpl,
    ): GetAllMoviesRepository

    @Reusable
    @Binds
    fun bindSearchMovieRepository(
        searchMovieRepository: SearchMovieRepositoryImpl,
    ): SearchMovieRepository

    @Reusable
    @Binds
    fun bindGetAllLocalMoviesRepository(
        getAllLocalMoviesRepository: GetAllLocalMoviesRepositoryImpl,
    ): GetAllLocalMoviesRepository

    @Reusable
    @Binds
    fun bindGetMovieDetailsRepository(
        getMovieDetailsRepository: GetMovieDetailsRepositoryImpl,
    ): GetMovieDetailsRepository

    @Reusable
    @Binds
    fun bindGetMoviePostersRepository(
        getMoviePostersRepository: GetMoviePostersRepositoryImpl,
    ): GetMoviePostersRepository

    @Reusable
    @Binds
    fun bindGetMovieReviewsRepository(
        getMovieReviewsRepository: GetMovieReviewsRepositoryImpl,
    ): GetMovieReviewsRepository
}
