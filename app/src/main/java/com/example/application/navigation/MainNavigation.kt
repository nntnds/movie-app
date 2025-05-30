package com.example.application.navigation

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.application.screens.DetailScreen
import com.example.application.screens.HomeScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.application.screens.FavoriteScreen

sealed class Screen() {
    @Serializable
    data object Home: Screen()

    @Serializable
    data object Favorite: Screen()

    @Serializable
    data class Detail(
        val id: Int,
        val imageUrl: String,
        val title: String
    ): Screen()
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.hasRoute(item.route::class)
                    } == true

                    NavigationBarItem(
                        icon = {
                            if (isSelected) Icon(item.selectedIcon, null)
                            else Icon(item.unSelectedIcon, null)
                        },
                        label = {
                            Text(item.label)
                        },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
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

            composable<Screen.Favorite> {
                FavoriteScreen()
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
}