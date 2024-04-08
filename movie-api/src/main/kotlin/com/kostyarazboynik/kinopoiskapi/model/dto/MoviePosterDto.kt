package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePosterDto(
    @SerialName("url") val url: String?,
    @SerialName("previewUrl") val previewUrl: String?,
)
