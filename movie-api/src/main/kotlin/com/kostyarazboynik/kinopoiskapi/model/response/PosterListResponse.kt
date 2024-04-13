package com.kostyarazboynik.kinopoiskapi.model.response

import com.kostyarazboynik.kinopoiskapi.model.dto.MoviePosterDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosterListResponse(
    @SerialName("docs") val postersList: List<MoviePosterDto>,
    @SerialName("total") val total: Int,
)
