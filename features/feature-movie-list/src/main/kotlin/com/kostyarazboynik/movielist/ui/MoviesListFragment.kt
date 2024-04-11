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
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponentProvider
import com.kostyarazboynik.movielist.ui.list_adapter.MoviesListAdapter
import com.kostyarazboynik.movielist.ui.utils.Constants
import com.kostyarazboynik.movielist.ui.utils.textChanges
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListFragmentViewModel by lazy {
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
        viewModel.loadAllData()
        setUpFilterOptions()
        setUpSearchField()
        setUpSwipeRefreshListener()
        setUpRecyclerView()
        updateUI()
    }

    private fun setUpFilterOptions() {
        binding.apply {
            filterOptions.apply {
                setAdapter(
                    ArrayAdapter(
                        context,
                        R.layout.selectable_item_layout,
                        resources.getStringArray(R.array.filtering_options).toList()
                    )
                )
                setOnItemClickListener { parent, _, position, _ ->
                    val item = parent.getItemAtPosition(position).toString().lowercase()
                    if (item == Constants.NONE) {
                        updateUI()
                        filterOptionTypesContainer.isVisible = false
                    } else {
                        setUpFilterOptionTypes(item)
                    }
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
                    filterOptionTypesContainer.isVisible = false
                    filterOptions.text.clear()
                    searchMovie.text.clear()
                    viewModel.loadLocalData()
                    isRefreshing = false
                }
            }
        }
    }

    private fun setUpFilterOptionTypes(item: String) {
        binding.apply {
            filterOptionTypes.apply {
                text.clear()
                setAdapter(
                    ArrayAdapter(
                        context,
                        R.layout.selectable_item_layout,
                        viewModel.getOption(item)
                    )
                )
                setOnItemClickListener { _, _, _, _ ->
                    updateUI()
                }
            }
            filterOptionTypesContainer.apply {
                isVisible = true
                hint = context.getString(R.string.select_option, item)
            }
        }
    }

    private fun setCurrentFilters(movies: List<Movie>) {
        binding.apply {
            listAdapter.submitList(
                if (filterOptions.text.toString().lowercase() == Constants.NONE ||
                    filterOptions.text.toString().isEmpty() ||
                    filterOptionTypes.text.toString().isEmpty()
                ) {
                    movies
                } else {
                    viewModel.filterMovies(
                        filterOptions.text.toString().lowercase(),
                        filterOptionTypes.text.toString(),
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
                    loadNewCompaniesCallBack = {
                        if (searchMovie.text.isEmpty()) {
                            viewModel.loadAllDataForced()
                        }
                    }
                )
                layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    private fun updateUI() {
        viewModel.viewModelScope.launch {
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

        fun newInstance() = MoviesListFragment()
    }
}
