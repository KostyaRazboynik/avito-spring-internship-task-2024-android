package com.kostyarazboynik.movielist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kostyarazboynik.utils.extensions.launchNamed
import com.kostyarazboynik.domain.model.UiState
import com.kostyarazboynik.feature_movie_list.databinding.FragmentMoviesListLayoutBinding
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponentProvider
import com.kostyarazboynik.movielist.ui.list_adapter.MoviesListAdapter
import javax.inject.Inject

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
        viewModel.loadRemoteData()
        setUpRecyclerView()
        updateUI()
    }

    //
    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            adapter = MoviesListAdapter(
                loadNewCompaniesCallBack = { }
            )
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    //
//    private fun setUpData() {
//        if (viewModel.isFirstInitializing()) {
//            binding.swipeRefreshLayout.waitLoadingUp()
//            viewModel.setInitialized()
//            viewModel.loadRemoteData()
//        } else {
//            viewModel.loadLocalData()
//        }
//    }
//
//    private fun View.waitLoadingUp() {
//        alpha = 0f
//        setLoaderUpVisibility(true)
//        animate()
//            .setDuration(COMPANIES_FIRST_LOADING_DURATION_MS)
//            .withEndAction {
//                setLoaderUpVisibility(false)
//                animate()
//                    .setDuration(SHOW_COMPANIES_LIST_DURATION_MS)
//                    .alpha(1f)
//                    .start()
//            }
//            .start()
//
//    }
//
//    private fun View.waitLoadingDown() {
//        setLoaderDownVisibility(true)
//        animate()
//            .setDuration(COMPANIES_FIRST_LOADING_DURATION_MS)
//            .withEndAction {
//                setLoaderDownVisibility(false)
//                viewModel.loadRemoteData()
//            }
//            .start()
//    }
//
//    private fun setLoaderUpVisibility(isVisible: Boolean) {
//        binding.apply {
//            progressLoaderUp.isVisible = isVisible
//            companiesLoadingUp.isVisible = isVisible
//        }
//    }
//
//    private fun setLoaderDownVisibility(isVisible: Boolean) {
//        binding.apply {
//            progressLoaderDown.isVisible = isVisible
//        }
//    }
//
    private fun updateUI() =
        viewModel.viewModelScope.launchNamed("$TAG-viewModelScope-updateUI") {
            updateStateUI()
        }

    //
    private suspend fun updateStateUI() {
        viewModel.companiesListFlow.collect { uiState ->
            when (uiState) {
                is UiState.Initial -> Unit
                is UiState.Success -> listAdapter.submitList(uiState.data)
                is UiState.Error -> {
                    when (uiState.code) {
                        400 -> makeToast(uiState.cause)
                        401 -> makeToast("401")
                        500 -> makeToast("500")
                        else -> makeToast("unknown")
                    }
                }
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MoviesListFragment"
        private const val COMPANIES_FIRST_LOADING_DURATION_MS = 3000L
        private const val SHOW_COMPANIES_LIST_DURATION_MS = 300L

        fun newInstance() = MoviesListFragment()
    }
}
