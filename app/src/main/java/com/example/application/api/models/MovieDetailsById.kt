package com.example.application.api.models

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class MovieDetailsById(
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview")val overView: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Float
)