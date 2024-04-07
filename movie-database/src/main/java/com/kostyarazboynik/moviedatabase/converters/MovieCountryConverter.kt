package com.kostyarazboynik.moviedatabase.converters

import androidx.room.TypeConverter
import com.kostyarazboynik.moviedatabase.model.MovieCountryDbo
import com.google.gson.Gson

internal object MovieCountryConverter{

    private val gson = Gson()

    @TypeConverter
    fun countryListToJson(countryListDbo: List<MovieCountryDbo>): String? {
        return gson.toJson(countryListDbo)
    }

    @TypeConverter
    fun countryListFromJson(json: String?): List<MovieCountryDbo> {
        return gson.fromJson(json, Array<MovieCountryDbo>::class.java).toList()
    }
}