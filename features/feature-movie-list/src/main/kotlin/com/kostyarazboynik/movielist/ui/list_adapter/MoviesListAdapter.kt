package com.kostyarazboynik.movielist.ui.list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_list.databinding.MovieListItemLayoutBinding
import com.kostyarazboynik.movielist.ui.list_adapter.diffutil.MoviesListDiffUtilCallback

class MoviesListAdapter(
    private val loadNewCompaniesCallBack: () -> Unit,
) : ListAdapter<Movie, MoviesListViewHolder>(MoviesListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder =
        MoviesListViewHolder(
            MovieListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (position == itemCount - 1 && currentList.size > 10 || currentList.size == 0) {
            loadNewCompaniesCallBack()
        }
    }

    private companion object {
        private const val TAG = "MoviesListAdapter"
    }
}
