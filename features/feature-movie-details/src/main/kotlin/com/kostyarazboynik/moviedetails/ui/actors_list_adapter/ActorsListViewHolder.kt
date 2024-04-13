package com.kostyarazboynik.moviedetails.ui.actors_list_adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kostyarazboynik.domain.model.movie.MoviePerson
import com.kostyarazboynik.feature_movie_details.databinding.ActorsListItemLayoutBinding

class ActorsListViewHolder(
    private val binding: ActorsListItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: MoviePerson) {
        binding.apply {
            actor.photo?.let { actorPhoto.load(it) }
            actor.name?.let { actorName.text = it }
            actor.description?.let { actorDescription.text = it }
        }
    }
}
