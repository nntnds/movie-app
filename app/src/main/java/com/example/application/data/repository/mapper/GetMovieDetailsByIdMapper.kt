package com.example.application.data.repository.mapper

import com.example.application.data.model.MovieDetailsByIdDto
import com.example.application.data.model.NowPlayingMoviesApiDto
import com.example.application.data.model.ResultApiDto
import com.example.application.domain.model.MovieDetailsById
import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.model.Results

object GetMovieDetailsByIdMapper {
    fun MovieDetailsByIdDto.toDomain(): MovieDetailsById {
        return MovieDetailsById(
            backdropPath = this.backdropPath,
            overView = this.overView,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
        )
    }
 }