package com.kostyarazboynik.moviedetails.ui.posters_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.feature_movie_details.databinding.PostersListItemLayoutBinding
import com.kostyarazboynik.moviedetails.ui.posters_list_adapter.diffutil.PostersListDiffUtilCallback
import com.kostyarazboynik.moviedetails.ui.reviews_list_adapter.ReviewsListViewHolder
import com.kostyarazboynik.moviedetails.ui.reviews_list_adapter.diffutil.ReviewsListDiffUtilCallback

class PostersListAdapter :
    ListAdapter<MoviePoster, PostersListViewHolder>(PostersListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostersListViewHolder =
        PostersListViewHolder(
            PostersListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    override fun onBindViewHolder(holder: PostersListViewHolder, position: Int) {
        val poster = getItem(position)
        holder.bind(poster)
    }

    private companion object {
        private const val TAG = "PostersListAdapter"
    }
}
