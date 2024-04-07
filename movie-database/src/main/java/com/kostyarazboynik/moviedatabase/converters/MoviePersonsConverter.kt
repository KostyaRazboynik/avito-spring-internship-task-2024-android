package com.kostyarazboynik.moviedatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kostyarazboynik.moviedatabase.model.MoviePersonDbo

internal object MoviePersonsConverter {

    private val gson = Gson()

    @TypeConverter
    fun personListToJson(personListDbo: List<MoviePersonDbo>): String {
        return gson.toJson(personListDbo)
    }

    @TypeConverter
    fun personListFromJson(json: String): List<MoviePersonDbo> {
        return gson.fromJson(json, Array<MoviePersonDbo>::class.java).toList()
    }
}