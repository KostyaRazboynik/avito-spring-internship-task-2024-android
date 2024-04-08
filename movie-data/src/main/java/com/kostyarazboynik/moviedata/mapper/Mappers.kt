package com.kostyarazboynik.moviedata.mapper

import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.model.movie.MovieCountry
import com.kostyarazboynik.domain.model.movie.MovieGenre
import com.kostyarazboynik.domain.model.movie.MoviePerson
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.model.movie.MovieRating
import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDetailedDto
import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDto
import com.kostyarazboynik.moviedatabase.model.MovieCountryDbo
import com.kostyarazboynik.moviedatabase.model.MovieDbo
import com.kostyarazboynik.moviedatabase.model.MovieGenreDbo
import com.kostyarazboynik.moviedatabase.model.MoviePosterDbo
import com.kostyarazboynik.moviedatabase.model.MovieRatingDbo


internal fun MovieDetailedDto.toMovie(): Movie =
    Movie(
        id = this.id,
        name = this.name,
        description = this.description,
        shortDescription = this.shortDescription,
        poster = MoviePoster(
            url = this.poster?.url,
            previewUrl = this.poster?.previewUrl,
        ),
        rating = MovieRating(kp = this.rating?.kp),
        ageRating = this.ageRating,
        year = this.year,
        genres = this.genres?.map { MovieGenre(name = it.name) },
        countries = this.countries?.map { MovieCountry(name = it.name) },
        persons = this.persons?.map { personDto ->
            MoviePerson(
                id = personDto.id,
                photo = personDto.photo,
                name = personDto.name,
                enName = personDto.enName,
                enProfession = personDto.enProfession,
                description = personDto.description,
            )
        },
        movieLength = this.movieLength,
        isSeries = this.isSeries,
    )

internal fun MovieDbo.toMovie(): Movie =
    Movie(
        id = this.id,
        name = this.name,
        description = this.description,
        shortDescription = this.shortDescription,
        poster = MoviePoster(
            url = this.poster?.url,
            previewUrl = this.poster?.previewUrl,
        ),
        rating = MovieRating(kp = this.rating?.kp),
        ageRating = this.ageRating,
        year = this.year,
        genres = this.genres?.map { MovieGenre(name = it.name) },
        countries = this.countries?.map { MovieCountry(name = it.name) },
        persons = null,
        isSeries = this.isSeries,
        movieLength = this.movieLength,
    )

internal fun MovieDto.toMovieDbo(): MovieDbo =
    MovieDbo(
        id = this.id,
        name = this.name,
        description = this.description,
        shortDescription = this.shortDescription,
        poster = MoviePosterDbo(
            url = this.poster?.url,
            previewUrl = this.poster?.previewUrl,
        ),
        rating = MovieRatingDbo(kp = this.rating?.kp),
        ageRating = this.ageRating,
        year = this.year,
        genres = this.genres?.map { MovieGenreDbo(name = it.name) },
        countries = this.countries?.map { MovieCountryDbo(name = it.name) },
        isSeries = this.isSeries,
        movieLength = this.movieLength,
    )

internal fun MovieDto.toMovie(): Movie =
    Movie(
        id = this.id,
        name = this.name,
        description = this.description,
        shortDescription = this.shortDescription,
        poster = MoviePoster(
            url = this.poster?.url,
            previewUrl = this.poster?.previewUrl,
        ),
        rating = MovieRating(kp = this.rating?.kp),
        ageRating = this.ageRating,
        year = this.year,
        genres = this.genres?.map { MovieGenre(name = it.name) },
        countries = this.countries?.map { MovieCountry(name = it.name) },
        persons = null,
        isSeries = this.isSeries,
        movieLength = this.movieLength,
    )
