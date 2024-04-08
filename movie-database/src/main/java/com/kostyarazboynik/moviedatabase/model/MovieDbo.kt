package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbo(
    @ColumnInfo("id") @PrimaryKey val id: Int,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("shortDescription") val shortDescription: String?,
    @ColumnInfo("year") val year: Int?,
    @Embedded(prefix = "rating_") val rating: MovieRatingDbo?,
    @ColumnInfo("ageRating") val ageRating: Int?,
    @Embedded(prefix = "poster_") val poster: MoviePosterDbo?,
    @ColumnInfo("genres") val genres: List<MovieGenreDbo>?,
    @ColumnInfo("countries") val countries: List<MovieCountryDbo>?,
    @ColumnInfo("isSeries") val isSeries: Boolean?,
    @ColumnInfo("movieLength") val movieLength: Int?,
)
