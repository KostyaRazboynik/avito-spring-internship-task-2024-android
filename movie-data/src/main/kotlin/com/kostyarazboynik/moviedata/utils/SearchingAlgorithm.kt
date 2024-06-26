package com.kostyarazboynik.moviedata.utils

import com.kostyarazboynik.domain.model.movie.Movie

/**
 * The simplest algorithm for comparing movie names and user input
 */
fun List<Movie>.search(movieName: String): List<Movie> {
    val movieNameParts = movieName.lowercase().split(" ").map { it.toSet() }
    return this.filter { movie ->
        val name = movie.name?.lowercase()?.split(" ")?.map { it.toSet() }
        var flag = true
        name?.forEachIndexed { index, _ ->
            if (index < movieNameParts.size) {
                flag = name[index].containsAll(movieNameParts[index])
            }
        }
        name != null && flag
    }
}
