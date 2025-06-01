package com.example.application.presentation.screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.data.api.ApiClient
import kotlinx.coroutines.launch
import com.example.application.presentation.screen.state.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadNowPlayingMovies()
    }

    fun loadNowPlayingMovies() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

            try {
                val result = ApiClient.api.getNowPlayingMovies(page = _state.value.pageCount)

                if (result.isSuccessful) {
                    result.body()?.results?.let { moviesData ->
                        _state.update {
                            it.copy(
                                nowPlayingMovies = it.nowPlayingMovies + moviesData,
                                pageCount = it.pageCount + 1,
                                isLoading = false
                            )
                        }

                    }
                }
            } catch (e: Exception) {
                Log.e("tagError", e.message, e)

            } finally {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}