package com.example.application.domain.repository

import com.example.application.domain.model.MovieDetailsById

interface DetailsRepository {
    suspend fun getDetailsMovieById(id: Int): List<MovieDetailsById>
}