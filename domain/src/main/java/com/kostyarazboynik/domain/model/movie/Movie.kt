package com.kostyarazboynik.domain.model.movie

data class Movie(
    val id: Int,
    val name: String?,
    val description: String?,
    val poster: MoviePoster?,
    val rating: MovieRating?,
    val ageRating: Int?,
    val year: Int?,
    val genres: List<MovieGenre>?,
    val countries: List<MovieCountry>?,
    val persons: List<MoviePerson>?,
    val movieLength: Int?,
    val isSeries: Boolean?,
)