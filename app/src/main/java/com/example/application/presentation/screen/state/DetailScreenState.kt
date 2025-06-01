package com.example.application.presentation.screen.state

import com.example.application.data.api.models.MovieDetailsById

data class DetailScreenState(
    val isFavorite: Boolean = false,
    val movieDetails: List<MovieDetailsById> = emptyList()
)