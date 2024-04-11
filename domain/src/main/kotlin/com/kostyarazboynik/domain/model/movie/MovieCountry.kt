package com.kostyarazboynik.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCountry(
    val name: String?,
) : Parcelable
