package com.kostyarazboynik.kinopoiskapi.model.response

import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("docs") val movieList: List<MovieDto>,
)