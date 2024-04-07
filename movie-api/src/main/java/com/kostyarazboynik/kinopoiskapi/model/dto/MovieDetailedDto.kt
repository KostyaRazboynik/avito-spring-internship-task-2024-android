package com.kostyarazboynik.kinopoiskapi.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailedDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("description") val description: String?,
    @SerialName("poster") val poster: MoviePosterDto?,
    @SerialName("rating") val rating: MovieRatingDto?,
    @SerialName("ageRating") val ageRating: Int?,
    @SerialName("year") val year: Int?,
    @SerialName("genres") val genres: List<MovieGenreDto>?,
    @SerialName("countries") val countries: List<MovieCountryDto>?,
    @SerialName("persons") val persons: List<MoviePersonDto>?,
    @SerialName("movieLength") val movieLength: Int?,
    @SerialName("isSeries") val isSeries: Boolean?,
)
