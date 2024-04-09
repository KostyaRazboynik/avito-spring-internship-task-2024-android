package com.kostyarazboynik.moviedata.utils

import com.kostyarazboynik.domain.model.movie.Movie
import com.kostyarazboynik.utils.Logger

/**
 * The simplest algorithm for comparing movie names and user input
 */
fun List<Movie>.search(movieName: String): List<Movie> {
    val movieNameParts = movieName.lowercase().split(" ").map { it.toSet() }
    Logger.d("search", movieNameParts.toString())
    return this.filter { movie ->
        val name = movie.name?.lowercase()?.split(" ")?.map { it.toSet() }
        Logger.d("search", name.toString())
        var flag = true
        name?.forEachIndexed { index, _ ->
            if (index < movieNameParts.size) {
                flag = name[index].containsAll(movieNameParts[index])
            }
        }
        name != null && flag
    }
}