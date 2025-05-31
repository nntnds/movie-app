package com.example.application.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.api.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.application.api.models.Results
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenViewModel : ViewModel() {
    var nowPlayingMovies = mutableStateListOf<Results>()
        private set

    val isLoading = MutableStateFlow(false)

    private var pageCount = MutableStateFlow(1)

    private var error = mutableStateOf(false)

    init {
        loadNowPlayingMovies()
    }

    fun loadNowPlayingMovies() {
        if (isLoading.value) return

        viewModelScope.launch {
            isLoading.value = true

            try {
                val result = ApiClient.api.getNowPlayingMovies(page = pageCount.value)

                if (result.isSuccessful) {
                    result.body()?.results?.let { moviesData ->
                        nowPlayingMovies.addAll(moviesData)
                        pageCount.value++
                    }
                }
            } catch (e: Exception) {
                Log.e("tagError", e.message, e)
                error.value = true

            } finally {
                isLoading.value = false
            }
        }
    }
}