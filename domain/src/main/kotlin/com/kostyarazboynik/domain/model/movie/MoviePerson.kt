package com.kostyarazboynik.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviePerson(
    val id: Int,
    val photo: String?,
    val name: String?,
    val enName: String?,
    val enProfession: String?,
    val description: String?,
) : Parcelable
