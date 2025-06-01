package com.example.application.presentation.screen.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.data.api.ApiClient
import com.example.application.data.database.MovieEntity
import com.example.application.data.repository.MovieRepository
import com.example.application.presentation.screen.state.DetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailScreenState())
    val state: StateFlow<DetailScreenState> = _state.asStateFlow()

    private val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        loadMovieDetails()
        checkFavorite(id)
    }

    fun checkFavorite(id: Int) = viewModelScope.launch {
        repository.checkIsFavorite(id).collect { newValue ->
                _state.update { it.copy(isFavorite = newValue) }
            }
    }

    fun loadMovieDetails() = viewModelScope.launch {
        try {
            val result = ApiClient.api.getDetailsMovieById(id)

            if (result.isSuccessful) {
                result.body()?.let { details ->
                    _state.update {
                        it.copy(
                            movieDetails = it.movieDetails + details
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("tagError", e.message, e)
        }
    }

    fun toggleFavorite(newValue: Boolean) {
        _state.update { it.copy(isFavorite = newValue) }
    }

    fun addToFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.addToFavorite(movieEntity)
    }

    fun removeFromFavorite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.removeFromFavorite(movieEntity)
    }
}