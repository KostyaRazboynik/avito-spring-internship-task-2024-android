package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo

data class MovieRatingDbo(
    @ColumnInfo("kp") val kp: Double,
)