package com.dicoding.jetpack

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.jetpack.ui.navigation.NavigationItem
import com.dicoding.jetpack.ui.navigation.Screen
import com.example.jetpack.ui.screen.add.AddScreen
import com.dicoding.jetpack.ui.screen.add.AddViewModel
import com.dicoding.jetpack.ui.screen.detail.DetailScreen
import com.dicoding.jetpack.ui.screen.favorite.FavoriteScreen
import com.dicoding.jetpack.ui.screen.home.HomeScreen
import com.dicoding.jetpack.ui.screen.profile.ProfileScreen

@Composable
fun AppNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailSerial.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    navigateToDetail = { serialId ->
                        navController.navigate(Screen.DetailSerial.createRoute(serialId))

                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailSerial.route,
                arguments = listOf(navArgument("serialId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("serialId") ?: -1L
                DetailScreen(
                    serialId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFav = {
                        // Handle navigate to favorite screen
                    }
                )
            }

            composable(Screen.Add.route) {
                val viewModel: AddViewModel = viewModel()

                AddScreen(
                    navController = navController,
                    onAddClick = { judul, deskripsi, rate ->
                    viewModel.addData(judul, deskripsi, rate)
                },
                    onImagePick = { uri ->
                        viewModel.setImageUri(uri)
                    }
                    )
            }

        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_home),
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.menu_favorite),
            icon = Icons.Default.Favorite,
            screen = Screen.Favorite
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profile),
            icon = Icons.Default.AccountCircle,
            screen = Screen.Profile
        ),
    )

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Transparent, // No background
        elevation = 0.dp // No shadow
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (currentRoute == item.screen.route) {
                            LocalContentColor.current.copy(alpha = 1f)
                        } else {
                            LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        }
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.screen.route) {
                            LocalContentColor.current.copy(alpha = 1f)
                        } else {
                            LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        }
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


