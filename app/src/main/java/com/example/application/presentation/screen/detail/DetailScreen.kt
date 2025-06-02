package com.example.application.presentation.screen.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.data.database.MovieEntity
import com.example.application.presentation.screen.detail.components.MovieContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    imageUrl: String,
    title: String,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val tempFavorite = !state.isFavorite
                            viewModel.toggleFavorite(tempFavorite)

                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = if (tempFavorite) "Добавлено в избранное"
                                    else "Удалено из избранного",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            if (tempFavorite) {
                                viewModel.addToFavorite(
                                    movieEntity = MovieEntity(id, title, imageUrl)
                                )
                            } else {
                                viewModel.removeFromFavorite(
                                    movieEntity = MovieEntity(id, title, imageUrl)
                                )
                            }
                        }
                    ) {
                        if (state.isFavorite) Icon(Icons.Filled.Favorite, null)
                        else Icon(Icons.Outlined.FavoriteBorder, null)
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.movieDetails.isNotEmpty()) {
            val details = state.movieDetails.first()

            MovieContent(
                innerPadding = innerPadding,
                imageUrl = imageUrl,
                title = title,
                backdropPath = details.backdropPath,
                overView = details.overView
            )
        }
    }
}