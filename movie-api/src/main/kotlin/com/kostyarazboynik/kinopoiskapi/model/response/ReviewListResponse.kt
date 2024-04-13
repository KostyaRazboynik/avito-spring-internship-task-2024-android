package com.kostyarazboynik.kinopoiskapi.model.response

import com.kostyarazboynik.kinopoiskapi.model.dto.MovieReviewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListResponse(
    @SerialName("docs") val reviewList: List<MovieReviewDto>,
    @SerialName("total") val total: Int,
)
