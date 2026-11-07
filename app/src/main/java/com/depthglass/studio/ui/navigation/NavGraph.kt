package com.depthglass.studio.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.depthglass.studio.ui.screens.*

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    data object Create : Screen("create", "Create", Icons.Outlined.AddCircle, Icons.Filled.AddCircle)
    data object Editor : Screen("editor", "3D Editor", Icons.Outlined.Layers, Icons.Filled.Layers)
    data object ClockWidgets : Screen("clock_widgets", "Clock & Widgets", Icons.Outlined.Watch, Icons.Filled.Watch)
    data object Gallery : Screen("gallery", "Gallery", Icons.Outlined.PhotoLibrary, Icons.Filled.PhotoLibrary)
    data object Settings : Screen("settings", "Settings", Icons.Outlined.Settings, Icons.Filled.Settings)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Create,
    Screen.Editor,
    Screen.ClockWidgets,
    Screen.Gallery
)

@Composable
fun DepthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateNew = { navController.navigate(Screen.Create.route) },
                onOpenEditor = { navController.navigate(Screen.Editor.route) },
                onOpenGallery = { navController.navigate(Screen.Gallery.route) }
            )
        }
        composable(Screen.Create.route) {
            CreateWallpaperScreen(
                onContinueToEditor = { navController.navigate(Screen.Editor.route) }
            )
        }
        composable(Screen.Editor.route) {
            DepthEditorScreen(
                onOpenClock = { navController.navigate(Screen.ClockWidgets.route) }
            )
        }
        composable(Screen.ClockWidgets.route) {
            ClockWidgetsScreen()
        }
        composable(Screen.Gallery.route) {
            GalleryScreen(
                onEditProject = { navController.navigate(Screen.Editor.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
