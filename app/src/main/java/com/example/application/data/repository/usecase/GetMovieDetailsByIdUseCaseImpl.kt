package com.example.application.data.repository.usecase

import com.example.application.domain.model.MovieDetailsById
import com.example.application.domain.repository.DetailsRepository
import com.example.application.domain.usecase.GetMovieDetailsByIdUseCase

class GetMovieDetailsByIdUseCaseImpl(
    private val repository: DetailsRepository
) : GetMovieDetailsByIdUseCase {
    override suspend fun execute(id: Int): Result<MovieDetailsById> {
        return repository.getDetailsMovieById(id)
    }
}