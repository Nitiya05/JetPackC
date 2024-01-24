package com.dicoding.jetpack.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Add : Screen("tambahkan")
    object DetailSerial : Screen("home/{serialId}") {
        fun createRoute(serialId: Long) = "home/$serialId"
    }
}
