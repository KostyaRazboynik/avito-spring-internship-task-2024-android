package com.kostyarazboynik.moviedetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_details.databinding.FragmentMovieDetailsLayoutBinding
import com.kostyarazboynik.moviedetails.dagger.FeatureMovieDetailsUiComponentProvider

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsFragmentViewModel by lazy {
        (context?.applicationContext as FeatureMovieDetailsUiComponentProvider)
            .provideFeatureMovieDetailsUiComponent().getViewModel()
    }

    private val binding: FragmentMovieDetailsLayoutBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG = "MoviesListFragment"
        private const val MOVIE_LIST_ITEM_KEY = "movie_list_item"

        fun newInstance(
            movie: Movie,
        ) = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE_LIST_ITEM_KEY, movie)
            }
        }
    }
}
