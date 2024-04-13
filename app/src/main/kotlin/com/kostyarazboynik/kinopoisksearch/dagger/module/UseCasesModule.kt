package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import com.kostyarazboynik.domain.repository.GetMovieDetailsRepository
import com.kostyarazboynik.domain.repository.GetMoviePostersRepository
import com.kostyarazboynik.domain.repository.GetMovieReviewsRepository
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.domain.usecase.GetAllLocalMoviesUseCase
import com.kostyarazboynik.domain.usecase.GetAllMoviesUseCase
import com.kostyarazboynik.domain.usecase.GetMovieDetailsUseCase
import com.kostyarazboynik.domain.usecase.GetMoviePostersUseCase
import com.kostyarazboynik.domain.usecase.GetMovieReviewsUseCase
import com.kostyarazboynik.domain.usecase.SearchMovieUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCasesModule {

    @Provides
    fun provideGetAllMoviesUseCase(getAllMoviesRepository: GetAllMoviesRepository): GetAllMoviesUseCase =
        GetAllMoviesUseCase(getAllMoviesRepository)

    @Provides
    fun provideSearchMovieUseCase(searchMovieRepository: SearchMovieRepository): SearchMovieUseCase =
        SearchMovieUseCase(searchMovieRepository)

    @Provides
    fun provideGetAllLocalMoviesUseCase(
        getAllLocalMoviesRepository: GetAllLocalMoviesRepository,
    ): GetAllLocalMoviesUseCase =
        GetAllLocalMoviesUseCase(getAllLocalMoviesRepository)

    @Provides
    fun provideGetMovieDetailsUseCase(getMovieDetailsRepository: GetMovieDetailsRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(getMovieDetailsRepository)

    @Provides
    fun provideGetAllMoviePostersUseCase(getMoviePostersRepository: GetMoviePostersRepository): GetMoviePostersUseCase =
        GetMoviePostersUseCase(getMoviePostersRepository)

    @Provides
    fun provideGetMovieReviewsUseCase(getMovieReviewsRepository: GetMovieReviewsRepository): GetMovieReviewsUseCase =
        GetMovieReviewsUseCase(getMovieReviewsRepository)
}
