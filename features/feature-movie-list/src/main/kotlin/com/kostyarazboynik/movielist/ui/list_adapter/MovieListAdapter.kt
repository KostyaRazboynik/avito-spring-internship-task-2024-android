package com.kostyarazboynik.movielist.ui.list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_list.databinding.MovieListItemLayoutBinding
import com.kostyarazboynik.movielist.ui.list_adapter.diffutil.MovieListDiffUtilCallback

class MovieListAdapter(
    private val loadNewMoviesCallBack: () -> Unit,
    private val onMovieClickListener: (movie: Movie) -> Unit,
) : ListAdapter<Movie, MovieListViewHolder>(MovieListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(
            MovieListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.apply {
            bind(movie)
            itemView.setOnClickListener {
                onMovieClickListener(movie)
            }
        }
        if (position == itemCount - 1) {
            loadNewMoviesCallBack()
        }
    }

    private companion object {
        private const val TAG = "MoviesListAdapter"
    }
}
