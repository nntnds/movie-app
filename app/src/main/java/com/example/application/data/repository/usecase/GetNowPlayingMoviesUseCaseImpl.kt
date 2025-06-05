package com.example.application.data.repository.usecase

import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.repository.NowPlayingRepository
import com.example.application.domain.usecase.GetNowPlayingMoviesUseCase

class GetNowPlayingMoviesUseCaseImpl(
    private val repository: NowPlayingRepository
) : GetNowPlayingMoviesUseCase {

    override suspend fun execute(page: Int): Result<NowPlayingMovies> {
        return repository.getNowPlayingMovies(page)
    }
}