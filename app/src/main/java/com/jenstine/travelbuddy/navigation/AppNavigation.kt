package com.jenstine.travelKing.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jenstine.travelKing.ui.screens.read.ReadScreen
import com.jenstine.travelKing.ui.screens.see.SeeScreen
import com.jenstine.travelKing.ui.screens.settings.SettingsScreen
import com.jenstine.travelKing.ui.screens.write.WriteScreen
import com.jenstine.travelKing.ui.screens.write.editor.EntryEditorScreen

sealed class Screen(val route: String) {
    object Read     : Screen("read")
    object See      : Screen("see")
    object Write    : Screen("write")
    object Settings : Screen("settings")
    object WriteEditor : Screen("write_editor/{entryId}") {
        fun createRoute(entryId: String?) = "write_editor/${entryId ?: "new"}"
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Read.route,
        modifier = modifier
    ) {
        composable(Screen.Read.route) { ReadScreen() }
        composable(Screen.See.route)  { SeeScreen() }
        composable(Screen.Write.route) {
            WriteScreen(
                onNavigateToEditor = { entryId ->
                    navController.navigate(Screen.WriteEditor.createRoute(entryId))
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.WriteEditor.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) {
            EntryEditorScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
