package com.example.application.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
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
import com.example.application.data.database.MovieEntity
import com.example.application.presentation.screen.viewmodel.DetailScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

//    var isFavorite by rememberSaveable { mutableStateOf(false) }

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

@Composable
fun MovieContent(
    innerPadding: PaddingValues,
    imageUrl: String,
    title: String,
    backdropPath: String,
    overView: String
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .height(320.dp)
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
                    .align(Alignment.Center)
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
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                text = overView,
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
        }
    }
}