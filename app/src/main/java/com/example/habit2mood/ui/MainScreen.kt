package com.example.habit2mood.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.habit2mood.ui.screens.DashboardScreen
import com.example.habit2mood.ui.screens.HabitScreen
import com.example.habit2mood.ui.screens.JournalScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Habit : Screen("habit", "Kebiasaan", Icons.Default.CheckCircle)
    object Journal : Screen("journal", "Jurnal Mood", Icons.Default.EditNote)
    object Dashboard : Screen("dashboard", "Ringkasan", Icons.Default.Dashboard)
}

@Composable
fun MainScreen(
    habitViewModel: HabitViewModel,
    journalViewModel: JournalViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screens = listOf(
        Screen.Habit,
        Screen.Journal,
        Screen.Dashboard
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Habit.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Habit.route) {
                HabitScreen(viewModel = habitViewModel)
            }
            composable(Screen.Journal.route) {
                JournalScreen(viewModel = journalViewModel)
            }
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    habitViewModel = habitViewModel,
                    journalViewModel = journalViewModel
                )
            }
        }
    }
}
