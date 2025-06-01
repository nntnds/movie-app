package com.example.application.presentation.screen.state

import com.example.application.data.database.MovieEntity

data class FavoriteScreenState(
    val favoriteMovies: List<MovieEntity> = emptyList()
)