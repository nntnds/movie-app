package com.example.application.data.repository.mapper

import com.example.application.data.api.model.NowPlayingMoviesApiDto
import com.example.application.data.api.model.ResultApiDto
import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.model.Results

object MovieMapper {
    fun NowPlayingMoviesApiDto.toDomain(): NowPlayingMovies {
        return NowPlayingMovies(
            results = this.results.map { it.toDomain() }
        )
    }

    private fun ResultApiDto.toDomain(): Results {
        return Results(
            id = this.id,
            posterPath = this.posterPath ?: "",
            title = this.title,
        )
    }
}