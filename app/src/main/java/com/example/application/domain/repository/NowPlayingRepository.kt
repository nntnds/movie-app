package com.example.application.domain.repository

import com.example.application.domain.model.NowPlayingMovies

interface NowPlayingRepository {
    suspend fun getNowPlayingMovies(page: Int): Result<NowPlayingMovies>
}