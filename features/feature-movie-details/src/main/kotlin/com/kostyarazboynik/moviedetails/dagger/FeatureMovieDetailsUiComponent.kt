package com.kostyarazboynik.moviedetails.dagger

import com.kostyarazboynik.dagger.FeatureScope
import com.kostyarazboynik.moviedetails.ui.MovieDetailsFragmentViewModel
import dagger.Subcomponent

/**
 * Dagger component for movie list feature
 */
@Subcomponent
@FeatureScope
interface FeatureMovieDetailsUiComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureMovieDetailsUiComponent
    }

    fun getViewModel(): MovieDetailsFragmentViewModel
}

interface FeatureMovieDetailsUiComponentProvider {
    fun provideFeatureMovieDetailsUiComponent(): FeatureMovieDetailsUiComponent
}
