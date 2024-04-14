package com.kostyarazboynik.kinopoiskapi

import androidx.annotation.IntRange
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kostyarazboynik.kinopoiskapi.model.dto.MovieDetailedDto
import com.kostyarazboynik.kinopoiskapi.model.response.MovieListResponse
import com.kostyarazboynik.kinopoiskapi.model.response.PosterListResponse
import com.kostyarazboynik.kinopoiskapi.model.response.ReviewListResponse
import com.kostyarazboynik.kinopoiskapi.utils.MoviesApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [API Documentation](https://api.kinopoisk.dev/documentation)
 */
interface MoviesApi {

    @GET("/v1.4/movie")
    suspend fun loadMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 30) limit: Int = 20,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
    ): Result<MovieListResponse>

    @GET("/v1.4/movie/{id}")
    suspend fun loadMovieDetail(
        @Path("id") @IntRange(from = 250, to = 7000000) movieId: Int,
    ): Result<MovieDetailedDto>

    @GET("/v1.4/movie/search")
    suspend fun searchMovie(
        @Query("query") searchName: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 20,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1,
    ): Result<MovieListResponse>

    @GET("/v1.4/review")
    suspend fun loadMovieReviews(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 20,
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Int,
    ): Result<ReviewListResponse>

    @GET("/v1.4/image")
    suspend fun loadMoviePosters(
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Int,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 30) limit: Int = 20,
    ): Result<PosterListResponse>
}

fun MoviesApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json { ignoreUnknownKeys = true },
): MoviesApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory(json))
    .addCallAdapterFactory(ResultCallAdapterFactory.create())
    .client(client(okHttpClient, apiKey))
    .build()

private fun client(
    okHttpClient: OkHttpClient?,
    apiKey: String,
): OkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
    .addInterceptor(MoviesApiKeyInterceptor(apiKey))
    .build()

private fun converterFactory(
    json: Json
) = json.asConverterFactory("application/json".toMediaType())
