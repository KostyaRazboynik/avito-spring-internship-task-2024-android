package com.kostyarazboynik.moviedatabase.model

import androidx.room.ColumnInfo

class MoviePersonDbo(
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("photo") val photo: String?,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("enName") val enName: String?,
    @ColumnInfo("enProfession") val enProfession: String?,
    @ColumnInfo("description") val description: String?,
)