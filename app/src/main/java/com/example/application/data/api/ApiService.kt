package com.example.application.data.api

import com.example.application.BuildConfig
import com.example.application.data.model.MovieDetailsByIdDto
import com.example.application.data.model.NowPlayingMoviesApiDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "ru",
    ): NowPlayingMoviesApiDto

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("movie/{id}")
    suspend fun getDetailsMovieById(
        @Path("id") id: Int,
        @Query("language") language: String = "ru",
    ): MovieDetailsByIdDto
}

