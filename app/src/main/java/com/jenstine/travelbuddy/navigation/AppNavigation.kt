package com.jenstine.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jenstine.travelbuddy.ui.screens.read.ReadScreen
import com.jenstine.travelbuddy.ui.screens.see.SeeScreen
import com.jenstine.travelbuddy.ui.screens.settings.SettingsScreen
import com.jenstine.travelbuddy.ui.screens.write.WriteScreen

sealed class Screen(val route: String) {
    object Read     : Screen("read")
    object See      : Screen("see")
    object Write    : Screen("write")
    object Settings : Screen("settings")
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Read.route,
        modifier = modifier
    ) {
        composable(Screen.Read.route)     { ReadScreen() }
        composable(Screen.See.route)      { SeeScreen() }
        composable(Screen.Write.route)    { WriteScreen() }
        composable(Screen.Settings.route) { SettingsScreen(onBack = { navController.popBackStack() }) }
    }
}
