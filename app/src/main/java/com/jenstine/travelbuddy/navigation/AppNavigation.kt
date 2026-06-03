package com.jenstine.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jenstine.travelbuddy.ui.screens.DetailScreen
import com.jenstine.travelbuddy.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(onNavigateToDetail = { navController.navigate(Screen.Detail.route) })
        }
        composable(Screen.Detail.route) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
