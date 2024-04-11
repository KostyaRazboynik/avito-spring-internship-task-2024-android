package com.kostyarazboynik.movielist.dagger

import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.movielist.ui.MovieListFragmentViewModel
import dagger.Subcomponent

/**
 * Dagger component for movie list feature
 */
@Subcomponent
@FeatureScope
interface FeatureMovieListUiComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureMovieListUiComponent
    }

    fun getViewModel(): MovieListFragmentViewModel
}

interface FeatureMovieListUiComponentProvider {
    fun provideFeatureMovieListUiComponent(): FeatureMovieListUiComponent
}
