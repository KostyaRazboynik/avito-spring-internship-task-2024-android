package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewDto(
    @SerialName("id") val id: Int,
    @SerialName("movieId") val movieId: Int?,
    @SerialName("author") val author: String?,
    @SerialName("title") val title: String?,
    @SerialName("review") val review: String?,
    @SerialName("type") val type: String?,
)
