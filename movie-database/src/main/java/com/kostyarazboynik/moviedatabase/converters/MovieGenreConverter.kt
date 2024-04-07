package com.kostyarazboynik.moviedatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kostyarazboynik.moviedatabase.model.MovieGenreDbo

internal object MovieGenreConverter {

    private val gson = Gson()

    @TypeConverter
    fun genreListToJson(genreListDbo: List<MovieGenreDbo>?): String? {
        return gson.toJson(genreListDbo)
    }

    @TypeConverter
    fun genreListFromJson(json: String?): List<MovieGenreDbo> {
        return gson.fromJson(json, Array<MovieGenreDbo>::class.java).toList()
    }
}