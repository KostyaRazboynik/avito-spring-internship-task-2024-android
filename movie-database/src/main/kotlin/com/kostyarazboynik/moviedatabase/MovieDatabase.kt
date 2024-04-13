package com.kostyarazboynik.moviedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kostyarazboynik.moviedatabase.converters.MovieCountryConverter
import com.kostyarazboynik.moviedatabase.converters.MovieGenreConverter
import com.kostyarazboynik.moviedatabase.dao.MovieDao
import com.kostyarazboynik.moviedatabase.model.MovieDbo


/**
 * trick: use 'implementation' room dependencies instead of 'api'
 * in build.gradle.kts(:movie-database).
 * thus, the app module doesn't know anything about room
 */
class MovieDatabase internal constructor(
    private val database: MovieRoomDatabase,
) {
    val moviesDbo: MovieDao
        get() = database.moviesDao()
}

@Database(entities = [MovieDbo::class], version = 1, exportSchema = false)
@TypeConverters(
    MovieCountryConverter::class,
    MovieGenreConverter::class,
)
internal abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
}

fun MovieDatabase(applicationContext: Context): MovieDatabase {
    val movieRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        MovieRoomDatabase::class.java,
        "movies",
    ).build()
    return MovieDatabase(movieRoomDatabase)
}
