package com.kostyarazboynik.kinopoisksearch.dagger.module

import com.kostyarazboynik.kinopoiskapi.MoviesApi
import com.kostyarazboynik.kinopoisksearch.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
object MoviesApiModule {

    @Provides
    @Singleton
    fun provideMoviesApi(okHttpClient: OkHttpClient?): MoviesApi =
        MoviesApi(
            baseUrl = BuildConfig.KINOPOISK_BASE_URL,
            apiKey = BuildConfig.KINOPOISK_API_KEY,
            okHttpClient = okHttpClient,
        )
}
