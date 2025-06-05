package com.example.application.presentation.screen.detail

import com.example.application.data.api.model.MovieDetailsByIdDto

data class DetailState(
    val isFavorite: Boolean = false,
    val movieDetails: List<MovieDetailsByIdDto> = emptyList()
)