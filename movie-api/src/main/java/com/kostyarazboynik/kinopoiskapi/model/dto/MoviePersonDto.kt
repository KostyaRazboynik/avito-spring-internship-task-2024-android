package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MoviePersonDto(
    @SerialName("id") val id: Int,
    @SerialName("photo") val photo: String?,
    @SerialName("name") val name: String?,
    @SerialName("enName") val enName: String?,
    @SerialName("enProfession") val enProfession: String?,
    @SerialName("description") val description: String?,
)