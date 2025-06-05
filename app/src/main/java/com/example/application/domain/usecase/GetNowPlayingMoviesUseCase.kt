package com.example.application.domain.usecase

import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.repository.NowPlayingRepository

interface GetNowPlayingMoviesUseCase {
    suspend fun execute(page: Int): Result<NowPlayingMovies>
}