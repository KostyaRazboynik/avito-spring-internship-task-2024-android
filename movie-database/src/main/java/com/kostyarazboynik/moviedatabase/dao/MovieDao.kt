package com.kostyarazboynik.moviedatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kostyarazboynik.moviedatabase.model.MovieDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieDbo>

    @Query("SELECT * FROM movies")
    fun getAllMoviesFlow(): Flow<List<MovieDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MovieDbo>)

    @Delete
    suspend fun remove(movies: List<MovieDbo>)
}
