package com.example.application.database

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    val allFavorite: Flow<List<MovieEntity>> = movieDao.getAllFavorites()

    suspend fun addToFavorite(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    suspend fun removeFromFavorite(movieEntity: MovieEntity) {
        movieDao.delete(movieEntity)
    }

    suspend fun checkIsFavorite(movieId: Int): Flow<Boolean> {
        return movieDao.getFavoriteById(movieId)
            .map { it != null }
            .catch { emit(false) }
    }
}