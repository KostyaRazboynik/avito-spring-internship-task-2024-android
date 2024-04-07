package com.kostyarazboynik.movielist.dagger

import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.movielist.ui.MoviesListFragmentViewModel
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

    fun getViewModel(): MoviesListFragmentViewModel
}

interface FeatureMovieListUiComponentProvider {
    fun provideFeatureMovieListUiComponent(): FeatureMovieListUiComponent
}
