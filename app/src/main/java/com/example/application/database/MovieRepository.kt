package com.example.application.database

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

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
}