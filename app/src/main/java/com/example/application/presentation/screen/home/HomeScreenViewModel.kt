package com.example.application.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.domain.model.NowPlayingMovies
import com.example.application.domain.repository.NowPlayingRepository
import com.example.application.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class HomeScreenViewModel(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadNowPlayingMovies()
    }

    fun loadNowPlayingMovies() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = false) }

            try {
                val response = withContext(Dispatchers.IO) {
                    getNowPlayingMoviesUseCase.execute(_state.value.pageCount)
                }

                response.onSuccess { value ->
                    _state.update {
                        it.copy(nowPlayingMovies = it.nowPlayingMovies + value)
                    }
                }

            } catch (e: Exception) {
                Log.e("tagError", e.message, e)
            }
        }
    }
}