package com.example.application.presentation.screen.state

import com.example.application.data.api.models.Results

data class HomeScreenState(
    val nowPlayingMovies: List<Results> = emptyList(),
    val isLoading: Boolean = false,
    val pageCount: Int = 1,
    val error: Boolean = false
)