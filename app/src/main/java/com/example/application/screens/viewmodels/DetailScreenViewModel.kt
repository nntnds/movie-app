package com.example.application.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.api.ApiClient
import com.example.application.api.models.MovieDetailsById
import com.example.application.database.MovieEntity
import com.example.application.database.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
) : ViewModel() {
    var isFavorite = mutableStateOf(false)

    private val id: Int = checkNotNull(savedStateHandle["id"])

    var movieDetails = mutableStateListOf<MovieDetailsById>()
        private set

    init {
        loadMovieDetails()
        checkFavorite(id)
    }

    fun loadMovieDetails() = viewModelScope.launch {
        try {
            val result = ApiClient.api.getDetailsMovieById(id)

            if (result.isSuccessful) {
                result.body()?.let { details ->
                    movieDetails.add(details)
                }
            }
        } catch (e: Exception) {
            Log.e("tagError", e.message, e)
        }
    }

    fun checkFavorite(id: Int) = viewModelScope.launch {
        repository.checkIsFavorite(id)
            .collect { isFavorite.value = it }
    }

    fun addToFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.addToFavorite(movieEntity)
    }

    fun removeFromFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.removeFromFavorite(movieEntity)
    }
}