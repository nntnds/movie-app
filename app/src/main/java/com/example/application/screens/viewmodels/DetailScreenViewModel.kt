package com.example.application.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.api.ApiClient
import com.example.application.api.models.MovieDetailsById
import com.example.application.database.MovieEntity
import com.example.application.database.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
) : ViewModel() {

//    val allFavorites: Flow<List<MovieEntity>> = repository.allFavorite

    fun addToFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.addToFavorite(movieEntity)
    }

    fun removeFromFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.removeFromFavorite(movieEntity)
    }

    var movieDetails = mutableStateListOf<MovieDetailsById>()
        private set

    private val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            try {
                val result = ApiClient.api.getDetailsMovieById(id = id)

                if (result.isSuccessful) {
                    result.body()?.let { details ->
                        movieDetails.add(details)
                    }
                }
            } catch (e: Exception) {
                Log.e("tagError", e.message, e)
            }
        }
    }
}