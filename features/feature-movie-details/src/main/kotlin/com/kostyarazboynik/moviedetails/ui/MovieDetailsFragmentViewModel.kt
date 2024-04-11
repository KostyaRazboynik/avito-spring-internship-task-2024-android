package com.kostyarazboynik.moviedetails.ui

import androidx.lifecycle.ViewModel
import com.kostyarazboynik.dagger.FeatureScope
import javax.inject.Inject

@FeatureScope
class MovieDetailsFragmentViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "MovieDetailsFragmentViewModel"
    }
}
