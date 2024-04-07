package com.kostyarazboynik.movielist.ui.list_adapter

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.feature_movie_list.R
import com.kostyarazboynik.feature_movie_list.databinding.MovieListItemLayoutBinding

class MoviesListViewHolder(
    private val binding: MovieListItemLayoutBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieShortInfo: Movie) {
        binding.apply {
            movieShortInfo.poster?.let { ivPoster.load(it.previewUrl) }
            movieShortInfo.rating?.kp?.let { rating ->
                tvRating.text = rating.toString()
                when (rating) {
                    in 7.0..10.0 -> tvRating.setBackgroundResource(R.drawable.circle_green)
                    in 5.0..6.9 -> tvRating.setBackgroundResource(R.drawable.circle_orange)
                    in 0.0..4.9 -> tvRating.setBackgroundResource(R.drawable.circle_red)
                    else -> {}
                }
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}