package com.example.application.presentation.screen.favorite

import com.example.application.data.database.MovieEntity

data class FavoriteState(
    val favoriteMovies: List<MovieEntity> = emptyList()
)