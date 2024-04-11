package com.kostyarazboynik.kinopoisksearch

import android.app.Application
import android.os.SystemClock
import com.kostyarazboynik.kinopoisksearch.dagger.AppComponent
import com.kostyarazboynik.moviedetails.dagger.FeatureMovieDetailsUiComponent
import com.kostyarazboynik.moviedetails.dagger.FeatureMovieDetailsUiComponentProvider
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponent
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponentProvider
import com.kostyarazboynik.utils.Logger
import com.kostyarazboynik.utils.timer.StartTimeHolder

class MoviesApp : Application(),
    FeatureMovieDetailsUiComponentProvider,
    FeatureMovieListUiComponentProvider {

    private var isInitialized = false

    override fun onCreate() {
        super.onCreate()
        Logger.setIsDebug(BuildConfig.DEBUG)
        Logger.d(TAG, "Start time is ${StartTimeHolder.timer.elapsed()}ms")

        appStartTime = SystemClock.elapsedRealtime()

        initialize()
    }

    private fun initialize() {
        if (isInitialized) {
            Logger.d(TAG, "already initialized")
            return
        }
        Logger.d(TAG, "initializing")
        isInitialized = true
        AppComponent.init(this)
        AppComponent.component.inject(this)
    }

    override fun provideFeatureMovieListUiComponent(): FeatureMovieListUiComponent =
        AppComponent.component.featureMovieListUiComponent().create()

    override fun provideFeatureMovieDetailsUiComponent(): FeatureMovieDetailsUiComponent =
        AppComponent.component.featureMovieDetailsUiComponent().create()

    companion object {
        private const val TAG = "MoviesApp"
        var appStartTime = 0L
            private set
    }
}