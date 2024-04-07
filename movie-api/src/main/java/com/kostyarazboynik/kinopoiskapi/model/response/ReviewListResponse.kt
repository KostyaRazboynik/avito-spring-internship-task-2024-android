package com.kostyarazboynik.kinopoiskapi.model.response

import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListResponse(
    @SerialName("docs") val reviewList: List<MovieDto>,
    @SerialName("total") val total: Int,
)
