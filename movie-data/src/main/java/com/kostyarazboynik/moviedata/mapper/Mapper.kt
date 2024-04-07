package com.kostyarazboynik.moviedata.mapper

import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDetailedDto
import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.domain.model.movie.MovieCountry
import com.kostyarazboynik.domain.model.movie.MovieGenre
import com.kostyarazboynik.domain.model.movie.MoviePerson
import com.kostyarazboynik.domain.model.movie.MoviePoster
import com.kostyarazboynik.domain.model.movie.MovieRating


fun MovieDetailedDto.toMovie(): Movie =
    Movie(
        id = this.id,
        name = this.name,
        description = this.description,
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
