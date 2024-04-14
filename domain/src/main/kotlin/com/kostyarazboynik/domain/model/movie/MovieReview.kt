package com.kostyarazboynik.domain.model.movie

data class MovieReview(
    val id: Int,
    val movieId: Int?,
    val author: String?,
    val title: String?,
    val review: String?,
    val type: String?,
)
