package com.kostyarazboynik.moviedetails.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.kostyarazboynik.domain.connection.ConnectivityObserver
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.model.movie.MoviePerson
import com.kostyarazboynik.feature_movie_details.R
import com.kostyarazboynik.feature_movie_details.databinding.FragmentMovieDetailsLayoutBinding
import com.kostyarazboynik.moviedetails.dagger.FeatureMovieDetailsUiComponentProvider
import com.kostyarazboynik.moviedetails.ui.actors_list_adapter.ActorsListAdapter
import com.kostyarazboynik.moviedetails.ui.posters_list_adapter.PostersListAdapter
import com.kostyarazboynik.moviedetails.ui.reviews_list_adapter.ReviewsListAdapter
import com.kostyarazboynik.moviedetails.utils.getList
import com.kostyarazboynik.utils.extensions.launchNamed
import kotlinx.coroutines.flow.collectLatest

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsFragmentViewModel by lazy {
        (context?.applicationContext as FeatureMovieDetailsUiComponentProvider)
            .provideFeatureMovieDetailsUiComponent().getViewModel()
    }

    private lateinit var binding: FragmentMovieDetailsLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getParcelable(MOVIE_LIST_ITEM_KEY) as? Movie)?.let { movie ->
            updateUi()
            setUpBackButton()
            checkInternetConnection(movie)
            updateUiState(UiState.Success(movie))
        }
    }

    private fun checkInternetConnection(movie: Movie) {
        viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-checkInternetConnection") {
            viewModel.internetStatus.collectLatest { status ->
                val visibility = status == ConnectivityObserver.Status.Available
                viewModel.loadMovie(movie)
                if (visibility) {
                    viewModel.loadMoviePosters(movie)
                    viewModel.loadMovieReviews(movie)
                }
                binding.apply {
                    actors.isVisible = visibility
                    actorsList.isVisible = visibility
                    posters.isVisible = visibility
                    postersList.isVisible = visibility
                    reviews.isVisible = visibility
                    reviewsList.isVisible = visibility
                }
            }
        }
    }

    private fun setUpBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun updateUi() {
        viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-updateUI") {
            viewModel.uiStateFlow.collectLatest { uiState ->
                updateUiState(uiState)
            }
        }
    }

    private fun updateUiState(uiState: UiState<Movie>) {
        when (uiState) {
            is UiState.Initial -> Unit
            is UiState.Loading -> uiState.data?.let { setUpMovieInfo(it) }
            is UiState.Success -> setUpMovieInfo(uiState.data)
            is UiState.Error -> uiState.data?.let { setUpMovieInfo(it) }
        }
    }

    @SuppressWarnings("MagicNumber")
    private fun setUpMovieInfo(movie: Movie) {
        binding.apply {
            movie.apply {
                poster?.let { moviePoster.load(it.url) }
                name?.let {
                    movieName.isVisible = true
                    movieName.text = name
                }
                description?.let {
                    movieName.isVisible = true
                    movieDescription.text = it
                }
                persons?.let { setUpActorsList(it) }

                try {
                    movieYearGenresCountriesLength.apply {
                        text = context?.getString(
                            R.string.movie_year_genres_countries_length,
                            year.toString(),
                            genres?.map { it.name },
                            countries?.map { it.name },
                            ((movieLength ?: 0) / 60).toString(),
                            ((movieLength ?: 0) - (((movieLength ?: 0) / 60) * 60))
                        )
                        isVisible = true
                    }
                } catch (_: Exception) {
                    movieYearGenresCountriesLength.isVisible = false
                }
            }
        }
        setUpRating(movie)
        setUpPostersList()
        setUpReviewList()
    }

    private fun setUpReviewList() {
        binding.reviewsRecyclerView.apply {
            adapter = ReviewsListAdapter().apply {
                viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-setUpReviews") {
                    viewModel.reviewStateFlow.collectLatest {
                        submitList(it.getList())
                    }
                }
            }
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false,
            )
        }
    }

    private fun setUpPostersList() {
        binding.postersRecyclerView.apply {
            adapter = PostersListAdapter().apply {
                viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-setUpPosters") {
                    viewModel.postersStateFlow.collectLatest {
                        submitList(it.getList())
                    }
                }
            }
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false,
            )
        }
    }

    @SuppressWarnings("MagicNumber")
    private fun setUpActorsList(actors: List<MoviePerson>) {
        binding.actorsRecyclerView.apply {
            adapter = ActorsListAdapter().apply {
                submitList(actors)
            }
            layoutManager = GridLayoutManager(
                context,
                4,
                GridLayoutManager.HORIZONTAL,
                false,
            )
        }
    }

    @SuppressLint("DefaultLocale")
    @SuppressWarnings("MagicNumber")
    private fun setUpRating(movie: Movie) {
        binding.apply {
            movieRating.apply {
                val rating = movie.rating?.kp
                if (rating == null) {
                    isVisible = false
                } else {
                    text = String.format("%.1f", rating)
                    when (rating) {
                        in 7.0..10.0 -> setTextColor(context.getColor(R.color.green))
                        in 5.0..6.9 -> setTextColor(context.getColor(R.color.orange))
                        in 0.0..4.9 -> setTextColor(context.getColor(R.color.red))
                        else -> Unit
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
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
