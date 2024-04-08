package com.kostyarazboynik.kinopoisksearch.dagger.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
object BuildTypeModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()
}