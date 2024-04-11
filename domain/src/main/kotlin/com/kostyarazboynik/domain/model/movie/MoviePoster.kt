package com.kostyarazboynik.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviePoster(
    val url: String?,
    val previewUrl: String?,
) : Parcelable