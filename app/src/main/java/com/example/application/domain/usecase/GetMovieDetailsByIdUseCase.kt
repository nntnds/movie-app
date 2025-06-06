package com.example.application.domain.usecase

import com.example.application.domain.model.MovieDetailsById

interface GetMovieDetailsByIdUseCase {
    suspend fun execute(id: Int): Result<MovieDetailsById>
}