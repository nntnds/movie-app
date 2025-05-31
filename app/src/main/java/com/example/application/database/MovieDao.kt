package com.example.application.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId LIMIT 1")
    fun getFavoriteById(movieId: Int): Flow<MovieEntity>
}