package com.kostyarazboynik.movielist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_list.R
import com.kostyarazboynik.feature_movie_list.databinding.FragmentMoviesListLayoutBinding
import com.kostyarazboynik.moviedetails.ui.MovieDetailsFragment
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponentProvider
import com.kostyarazboynik.movielist.ui.list_adapter.MoviesListAdapter
import com.kostyarazboynik.movielist.ui.utils.Constants
import com.kostyarazboynik.movielist.ui.utils.textChanges
import com.kostyarazboynik.utils.extensions.launchNamed
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    private val viewModel: MovieListFragmentViewModel by lazy {
        (context?.applicationContext as FeatureMovieListUiComponentProvider)
            .provideFeatureMovieListUiComponent().getViewModel()
    }

    private val binding: FragmentMoviesListLayoutBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val listAdapter: MoviesListAdapter
        get() = binding.recyclerView.adapter as MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadLocalData()
        setUpFilterOptions()
        setUpSearchField()
        setUpSwipeRefreshListener()
        setUpRecyclerView()
        updateUI()
    }

    private fun setUpFilterOptions() {
        binding.apply {
            filterType.apply {
                viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-collect-filterType") {
                    viewModel.filterTypeStateFlow.collectLatest { filterType ->
                        if (filterType == Constants.NONE) {
                            filterOptionContainer.isVisible = false
                        } else {
                            filterOptionContainer.isVisible = true
                            setUpFilterOptionType(filterType)
                        }
                    }
                }
                setAdapter(
                    ArrayAdapter(
                        context,
                        R.layout.selectable_item_layout,
                        resources.getStringArray(R.array.filtering_options).toList()
                    )
                )
                setOnItemClickListener { parent, _, position, _ ->
                    val item = parent.getItemAtPosition(position).toString().lowercase()
                    viewModel.setUpFilterType(item)
                    updateUI()
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setUpSearchField() {
        binding.searchMovie.apply {
            textChanges()
                .filterNot { it.isNullOrBlank() }
                .debounce(1000)
                .distinctUntilChanged()
                .onEach {
                    viewModel.searchMovie(this.text.toString())
                }
                .launchIn(viewModel.viewModelScope)
        }
    }

    private fun setUpSwipeRefreshListener() {
        binding.apply {
            swipeRefreshLayout.apply {
                setOnRefreshListener {
                    filterOptionContainer.isVisible = false
                    filterType.text.clear()
                    searchMovie.text.clear()
                    viewModel.loadLocalData()
                    isRefreshing = false
                }
            }
        }
    }

    private fun setUpFilterOptionType(item: String) {
        binding.apply {
            filterOption.apply {
                text.clear()
                setAdapter(
                    ArrayAdapter(
                        context,
                        R.layout.selectable_item_layout,
                        viewModel.getOption(item)
                    )
                )
            }
            filterOptionContainer.hint = context?.getString(R.string.select_option, item)
        }
    }

    private fun setCurrentFilters(movies: List<Movie>) {
        binding.apply {
            listAdapter.submitList(
                if (filterType.text.toString().lowercase() == Constants.NONE ||
                    filterType.text.toString().isEmpty() ||
                    filterOption.text.toString().isEmpty()
                ) {
                    movies
                } else {
                    filterOptionContainer.isVisible = true
                    viewModel.filterMovies(
                        filterType.text.toString().lowercase(),
                        filterOption.text.toString(),
                        movies
                    )
                }
            )
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            recyclerView.apply {
                adapter = MoviesListAdapter(
                    loadNewMoviesCallBack = {
                        if (searchMovie.text.isEmpty()) {
                            viewModel.loadAllData()
                        }
                    },
                    onMovieClickListener = { movie ->
                        navigateToMovieDetailsFragment(movie)
                    },
                )
                layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    private fun navigateToMovieDetailsFragment(movie: Movie) {
        activity?.supportFragmentManager?.let { manager ->
            arguments?.getInt(FRAME_CONTENT_ID)?.let { id ->
                val transaction = manager.beginTransaction()
                val fragment = MovieDetailsFragment.newInstance(movie)
                transaction.replace(id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun updateUI() {
        viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-updateUI") {
            viewModel.uiStateFlow.collect { uiState ->
                updateUIState(uiState)
            }
        }
    }

    private fun updateUIState(uiState: UiState<List<Movie>>) {
        when (uiState) {
            is UiState.Initial -> Unit
            is UiState.Loading -> setCurrentFilters(uiState.data ?: listOf())
            is UiState.Success -> setCurrentFilters(uiState.data)
            is UiState.Error -> makeToast(uiState.cause ?: "error")
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MoviesListFragment"

        private const val FRAME_CONTENT_ID = "frame_content_id"

        fun newInstance(
            frameContentId: Int,
        ) = MovieListFragment().apply {
            arguments = Bundle().apply {
                putInt(FRAME_CONTENT_ID, frameContentId)
            }
        }
    }
}
