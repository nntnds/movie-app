package com.example.application.presentation.screen.detail

import com.example.application.data.api.models.MovieDetailsById

data class DetailState(
    val isFavorite: Boolean = false,
    val movieDetails: List<MovieDetailsById> = emptyList()
)