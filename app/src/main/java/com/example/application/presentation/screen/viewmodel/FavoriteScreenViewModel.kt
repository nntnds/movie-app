package com.example.application.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.data.repository.MovieRepository
import com.example.application.presentation.screen.state.FavoriteScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        repository.allFavorite.collect { movies ->
            _state.update {
                it.copy(
                    favoriteMovies = it.favoriteMovies + movies
                )
            }
        }
    }
}