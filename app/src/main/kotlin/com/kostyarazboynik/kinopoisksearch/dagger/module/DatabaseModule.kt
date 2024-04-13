package com.kostyarazboynik.kinopoisksearch.dagger.module

import android.content.Context
import com.kostyarazboynik.moviedatabase.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(applicationContext: Context): MovieDatabase =
        MovieDatabase(applicationContext)
}
