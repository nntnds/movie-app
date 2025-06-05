package com.example.application.data.api.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesApiDto(
    val results: List<ResultApiDto>
)

data class ResultApiDto(
    val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    val title: String,
)
