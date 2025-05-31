package com.example.application.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.api.models.MovieDetailsById
import com.example.application.database.MovieEntity
import com.example.application.database.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val favoriteMovies: StateFlow<List<MovieEntity>> = _favoriteMovies.asStateFlow()

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        repository.allFavorite.collect { movies ->
            _favoriteMovies.value = movies
        }
    }
}