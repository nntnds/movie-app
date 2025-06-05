package com.example.application.data.repository

import com.example.application.data.api.ApiService
import com.example.application.data.repository.mapper.MovieMapper
import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.repository.NowPlayingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class NowPlayingRepositoryImpl(
    private val apiService: ApiService,
    private val movieMapper: MovieMapper
) : NowPlayingRepository {

    override suspend fun getNowPlayingMovies(page: Int): Result<NowPlayingMovies> {
        return try {
            val response = apiService.getNowPlayingMovies(page)

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