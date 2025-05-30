package com.example.application.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import com.example.application.database.MovieEntity
import com.example.application.screens.viewmodels.DetailScreenViewModel
import kotlinx.coroutines.CoroutineScope
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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            topBar(
                scope,
                snackbarHostState,
                navigateBack,
                id,
                title,
                imageUrl
            )
        }
    ) { innerPadding ->
        if (viewModel.movieDetails.isNotEmpty()) {
            val movie = viewModel.movieDetails.first()

            MovieContent(
                innerPadding = innerPadding,
                imageUrl = imageUrl,
                title = title,
                backdropPath = movie.backdropPath,
            )
        }
    }
}

@Composable
fun MovieContent(
    innerPadding: PaddingValues,
    imageUrl: String,
    title: String,
    backdropPath: String,
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500$backdropPath")
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500$imageUrl")
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shadow(elevation = 10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(width = 200.dp, height = 300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit,
    id: Int,
    title: String,
    imageUrl: String,
    viewModel: DetailScreenViewModel = hiltViewModel(),
) {
    var isFavorite = viewModel.isFavorite
    TopAppBar(
        title = {
            Text("")
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, null)
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isFavorite.value = !isFavorite.value
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message =
                                if (isFavorite.value) "Добавлено в избранное"
                                else "Удалено из избранного",
                            duration = SnackbarDuration.Short
                        )
                    }
                    if (isFavorite.value) viewModel.addToFavorite(
                        movieEntity = MovieEntity(
                            id = id,
                            title = title,
                            imageUrl = imageUrl
                        )
                    ) else viewModel.removeFromFavorite(
                        movieEntity = MovieEntity(
                            id = id,
                            title = title,
                            imageUrl = imageUrl
                        )
                    )
                }
            ) {
                if (isFavorite.value) Icon(Icons.Default.Favorite, null)
                else Icon(Icons.Default.FavoriteBorder, null)
            }
        }
    )
}