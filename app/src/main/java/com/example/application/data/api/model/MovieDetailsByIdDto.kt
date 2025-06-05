package com.example.application.data.api.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsByIdDto(
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview")val overView: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Float
)