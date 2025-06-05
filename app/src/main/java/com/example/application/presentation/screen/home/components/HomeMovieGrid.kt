package com.example.application.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.size.Size
import com.example.application.data.api.model.MovieDto

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeMovieGrid(
    movieDtos: List<MovieDto>,
    gridState: LazyGridState,
    onNavigate: (Int, String, String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
    ) {
        itemsIndexed(
            items = movieDtos,
            key = { index, item ->
                "${item.id}_$index"
            }
        ) { index, item ->
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .animateItem()
                    .height(270.dp)
                    .clickable(
                        onClick = {
                            onNavigate(
                                item.id,
                                item.posterPath ?: "",
                                item.title,
                            )
                        }
                    )
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" })
                            .size(Size.ORIGINAL)
                            .allowHardware(true)
                            .crossfade(false)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCacheKey(item.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" })
                            .memoryCacheKey(item.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" })
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
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.4f)
                                    ),
                                )
                            )
                    ) {
                        Text(
                            text = item.title,
                            modifier = Modifier
                                .padding(start = 10.dp, bottom = 10.dp)
                                .align(Alignment.BottomStart),
                            color = Color.White,
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp,
                        )
                    }
                }
            }
        }
    }
}