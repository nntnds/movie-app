package com.example.application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.application.screens.DetailScreen
import com.example.application.screens.HomeScreen
import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    data object Home: Screen()

    @Serializable
    data class Detail(
        val id: Int,
        val imageUrl: String,
        val title: String
    ): Screen()
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onNavigate = { id, imageUrl, title ->
                    navController.navigate(
                        Screen.Detail(id, imageUrl, title)
                    )
                }
            )
        }

        composable<Screen.Detail> {
            val args = it.toRoute<Screen.Detail>()
            DetailScreen(
                id = args.id,
                imageUrl = args.imageUrl,
                title = args.title,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}