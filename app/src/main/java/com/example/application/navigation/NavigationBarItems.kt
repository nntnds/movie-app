package com.example.application.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem<T: Any>(
    val route: T,
    val label: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val NavigationBarItems = listOf(
    NavigationBarItem(
        route = Screen.Home,
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),

    NavigationBarItem(
        route = Screen.Favorite,
        label = "Favorite",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.FavoriteBorder,
    ),
)