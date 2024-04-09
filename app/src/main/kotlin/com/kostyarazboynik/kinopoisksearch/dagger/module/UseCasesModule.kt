package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.domain.repository.GetAllLocalMoviesRepository
import com.kostyarazboynik.domain.repository.GetAllMoviesRepository
import com.kostyarazboynik.domain.repository.SearchMovieRepository
import com.kostyarazboynik.domain.usecase.GetAllLocalMoviesUseCase
import com.kostyarazboynik.domain.usecase.GetAllMoviesUseCase
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
    fun provideUseCase(getAllLocalMoviesRepository: GetAllLocalMoviesRepository): GetAllLocalMoviesUseCase =
        GetAllLocalMoviesUseCase(getAllLocalMoviesRepository)
}
