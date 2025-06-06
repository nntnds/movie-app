package com.example.application.data.di

import android.content.Context
import androidx.room.Room
import com.example.application.data.database.MovieDao
import com.example.application.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    fun provideDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}