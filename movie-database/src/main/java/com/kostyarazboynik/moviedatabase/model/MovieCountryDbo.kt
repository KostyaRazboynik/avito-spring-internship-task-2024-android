package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo

data class MovieCountryDbo(
    @ColumnInfo("name") val name: String?,
)
