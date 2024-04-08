package com.kostyarazboynik.movielist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.feature_movie_list.databinding.FragmentMoviesListLayoutBinding
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponentProvider
import com.kostyarazboynik.movielist.ui.list_adapter.MoviesListAdapter
import com.kostyarazboynik.utils.extensions.launchNamed

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
        setUpRecyclerView()
        updateUI()
    }


    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            adapter = MoviesListAdapter(
                loadNewCompaniesCallBack = { }
            )
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun updateUI() =
        viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-updateUI") {
            updateStateUI()
        }

    private suspend fun updateStateUI() {
        viewModel.stateFlow.collect { uiState ->
            when (uiState) {
                is UiState.Initial -> Unit
                is UiState.Loading -> {
                    makeToast("loading")
                    listAdapter.submitList(uiState.data?: listOf())
                }
                is UiState.Success -> listAdapter.submitList(uiState.data)
                is UiState.Error<*> -> makeToast(uiState.cause ?: "error")
            }
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
