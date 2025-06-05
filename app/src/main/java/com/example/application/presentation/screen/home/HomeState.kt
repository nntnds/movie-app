package com.example.application.presentation.screen.home

import com.example.application.domain.model.NowPlayingMovies

data class HomeState(
    val nowPlayingMovies: List<NowPlayingMovies> = emptyList(),
    val isLoading: Boolean = false,
    val pageCount: Int = 1,
    val error: Boolean = false
)