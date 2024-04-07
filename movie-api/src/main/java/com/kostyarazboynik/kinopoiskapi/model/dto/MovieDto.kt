package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
)
