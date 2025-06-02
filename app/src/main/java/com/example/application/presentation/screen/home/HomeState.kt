package com.example.application.presentation.screen.home

import com.example.application.data.api.models.Results

data class HomeState(
    val nowPlayingMovies: List<Results> = emptyList(),
    val isLoading: Boolean = false,
    val pageCount: Int = 1,
    val error: Boolean = false
)