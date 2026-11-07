package com.depthglass.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.depthglass.studio.ui.navigation.DepthNavGraph
import com.depthglass.studio.ui.navigation.Screen
import com.depthglass.studio.ui.navigation.bottomNavItems
import com.depthglass.studio.ui.theme.DepthGlassTheme
import com.depthglass.studio.ui.theme.GlassBlack
import com.depthglass.studio.ui.theme.TextPrimary
import com.depthglass.studio.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DepthGlassTheme {
                DepthGlassAppRoot()
            }
        }
    }
}

@Composable
fun DepthGlassAppRoot() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = GlassBlack,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xEE12121A),
                tonalElevation = 0.dp
            ) {
                bottomNavItems.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selected) screen.selectedIcon else screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                color = if (selected) TextPrimary else TextSecondary
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF4FC3F7),
                            unselectedIconColor = TextSecondary,
                            indicatorColor = Color(0x334FC3F7)
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            DepthNavGraph(navController = navController)
        }
    }
}
