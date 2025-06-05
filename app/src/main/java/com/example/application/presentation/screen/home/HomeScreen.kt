package com.example.application.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.application.presentation.screen.home.components.HomeMovieGrid
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    onNavigate: (Int, String, String) -> Unit,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val gridState = rememberLazyGridState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                if (lastItem >= totalItems - 8 && totalItems > 0 && !state.isLoading) {
                    delay(1000L)
                    viewModel.loadNowPlayingMovies()
                }
            }
    }

    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            HomeMovieGrid(
                movieDtos = state.nowPlayingMovies,
                gridState = gridState,
                onNavigate = onNavigate
            )
        }
    }
}