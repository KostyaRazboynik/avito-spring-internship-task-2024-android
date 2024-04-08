package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "posters")
data class MoviePosterDbo(
    @ColumnInfo("url") val url: String?,
    @ColumnInfo("previewUrl") val previewUrl: String?,
)