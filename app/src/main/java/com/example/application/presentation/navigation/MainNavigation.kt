package com.example.application.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.application.presentation.screen.DetailScreen
import com.example.application.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.application.presentation.screen.FavoriteScreen

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
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isDetailScreen = currentDestination?.hasRoute(
        Screen.Detail::class
    ) == true

    Scaffold(
        topBar = {
            when {
                currentDestination?.hasRoute(Screen.Home::class) == true -> {
                    TopAppBar(
                        title = { Text("Главная") }
                    )
                }
                currentDestination?.hasRoute(Screen.Favorite::class) == true -> {
                    TopAppBar(
                        title = { Text("Закладки") }
                    )
                }
                currentDestination?.hasRoute(Screen.Detail::class) == true -> false
            }
        },
        bottomBar = {
            if (!isDetailScreen) {
                NavigationBar {
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
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
//            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Home> {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigate = { id, imageUrl, title ->
                        navController.navigate(
                            Screen.Detail(id, imageUrl, title)
                        )
                    }
                )
            }

            composable<Screen.Favorite> {
                FavoriteScreen(
                    modifier = Modifier.padding(innerPadding),
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
}