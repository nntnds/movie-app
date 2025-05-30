package com.example.application.api.models

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class NowPlayingMovies(
    val results: List<Results>
)

@Stable
data class Results(
    val id: Int,
    @SerializedName("poster_path") val posterPath: String? = null,
    val title: String,
)
