package com.kostyarazboynik.movielist.ui.list_adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_list.R
import com.kostyarazboynik.feature_movie_list.databinding.MovieListItemLayoutBinding

class MovieListViewHolder(
    private val binding: MovieListItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    @SuppressWarnings("MagicNumber")
    fun bind(movie: Movie) {
        binding.apply {
            movie.poster?.let { moviePoster.load(it.previewUrl) }
            movie.rating?.kp?.let { rating ->
                movieRating.apply {
                    text = String.format("%.1f", rating)
                    when (rating) {
                        in 7.0..10.0 -> setBackgroundResource(R.drawable.circle_green)
                        in 5.0..6.9 -> setBackgroundResource(R.drawable.circle_orange)
                        in 0.0..4.9 -> setBackgroundResource(R.drawable.circle_red)
                        else -> Unit
                    }
                }
            }
        }
    }
}
