package com.kostyarazboynik.moviedatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kostyarazboynik.moviedatabase.model.MovieDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMoviesFlow(): Flow<List<MovieDbo>>

    @Insert
    suspend fun insert(movies: List<MovieDbo>)

    @Delete
    suspend fun remove(movies: List<MovieDbo>)

//    @Query("DELETE FROM movies")
//    suspend fun clean(movies: List<MovieDbo>)
}