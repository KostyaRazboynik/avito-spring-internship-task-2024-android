package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("description") val description: String?,
    @SerialName("shortDescription") val shortDescription: String?,
    @SerialName("year") val year: Int?,
    @SerialName("rating") val rating: MovieRatingDto?,
    @SerialName("ageRating") val ageRating: Int?,
    @SerialName("poster") val poster: MoviePosterDto?,
    @SerialName("genres") val genres: List<MovieGenreDto>?,
    @SerialName("countries") val countries: List<MovieCountryDto>?,
    @SerialName("isSeries") val isSeries: Boolean?,
    @SerialName("movieLength") val movieLength: Int?,
)
