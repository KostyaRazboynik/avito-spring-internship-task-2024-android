package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kostyarazboynik.moviedatabase.converters.MovieCountryConverter
import com.kostyarazboynik.moviedatabase.converters.MovieGenreConverter
import com.kostyarazboynik.moviedatabase.converters.MoviePersonsConverter

@Entity(tableName = "movies")
data class MovieDbo(
    @ColumnInfo("id") @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("description") val description: String?,
    @Embedded(prefix = "poster_") val poster: MoviePosterDbo?,
    @Embedded(prefix = "rating_") val rating: MovieRatingDbo?,
    @ColumnInfo("ageRating") val ageRating: Int?,
    @ColumnInfo("year") val year: Int?,
    @ColumnInfo("genres") val genres: List<MovieGenreDbo>?,
    @ColumnInfo("countries") val countries: List<MovieCountryDbo>,
    @ColumnInfo("persons") val persons: List<MoviePersonDbo>,
    @ColumnInfo("movieLength") val movieLength: Int?,
    @ColumnInfo("isSeries") val isSeries: Boolean?,
)
