package com.kostyarazboynik.moviedetails.ui.actors_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostyarazboynik.domain.model.movie.MoviePerson
import com.kostyarazboynik.feature_movie_details.databinding.ActorsListItemLayoutBinding
import com.kostyarazboynik.moviedetails.ui.actors_list_adapter.diffutil.ActorsListDiffUtilCallback

class ActorsListAdapter: ListAdapter<MoviePerson, ActorsListViewHolder>(ActorsListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsListViewHolder =
        ActorsListViewHolder(
            ActorsListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: ActorsListViewHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
    }

    private companion object {
        private const val TAG = "ActorsListAdapter"
    }
}
