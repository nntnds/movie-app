package com.example.application.domain.model

data class NowPlayingMovies(
    val results: List<Results>
)

data class Results(
    val id: Int,
    val posterPath: String,
    val title: String,
)
