package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo

data class MovieGenreDbo(
    @ColumnInfo("name") val name: String?,
)
