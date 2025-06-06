package com.example.application.data.repository

import com.example.application.data.api.ApiService
import com.example.application.data.model.MovieDetailsByIdDto
import com.example.application.data.repository.mapper.GetMovieDetailsByIdMapper
import com.example.application.domain.model.MovieDetailsById
import com.example.application.domain.repository.DetailsRepository
import com.example.application.domain.usecase.GetMovieDetailsByIdUseCase
import java.io.IOException

class DetailsRepositoryImpl(
    private val apiService: ApiService,
    private val movieMapper: GetMovieDetailsByIdMapper
) : DetailsRepository {

    override suspend fun getDetailsMovieById(id: Int): Result<MovieDetailsById> {
        return try {
            val response = apiService.getDetailsMovieById(id)

            val domainData = with(movieMapper) {
                response.toDomain()
            }

            Result.success(domainData)
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected error: ${e.localizedMessage}"))
        }
    }
}