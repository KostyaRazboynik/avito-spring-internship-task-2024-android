package com.kostyarazboynik.moviedetails.ui.posters_list_adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.feature_movie_details.databinding.PostersListItemLayoutBinding

class PostersListViewHolder(
    private val binding: PostersListItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(poster: MoviePoster) {
        poster.url?.let { binding.poster.load(it) }
    }
}
