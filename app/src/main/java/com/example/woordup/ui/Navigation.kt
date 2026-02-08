package com.example.woordup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.woordup.ui.screens.AddWordScreen
import com.example.woordup.ui.screens.HomeScreen
import com.example.woordup.ui.screens.OnboardingScreen
import com.example.woordup.ui.screens.ProfileScreen
import com.example.woordup.ui.screens.VocabularyListScreen
import com.example.woordup.ui.theme.DeepGreen
import com.example.woordup.viewmodel.WordViewModel

@Composable
fun WoordUpApp(viewModel: WordViewModel) {
    val navController = rememberNavController()
    val username by viewModel.username.collectAsState()
    val startDestination = if (username.isNotBlank()) "home" else "onboarding"
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf("home", "vocabulary", "profile")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = com.example.woordup.ui.theme.White
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
                        label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepGreen,
                            selectedTextColor = DeepGreen,
                            indicatorColor = DeepGreen.copy(alpha = 0.1f)
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.List, contentDescription = "Vocabulary") },
                        label = { Text("Vocabulary", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == "vocabulary",
                        onClick = {
                            navController.navigate("vocabulary") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepGreen,
                            selectedTextColor = DeepGreen,
                            indicatorColor = DeepGreen.copy(alpha = 0.1f)
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Rounded.Person, contentDescription = "Profile") },
                        label = { Text("Profile", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == "profile",
                        onClick = {
                            navController.navigate("profile") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepGreen,
                            selectedTextColor = DeepGreen,
                            indicatorColor = DeepGreen.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onStartClick = {
                        viewModel.saveUser("Explorer", 1)
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onAddWordClick = { navController.navigate("add_word") },
                    onReviewClick = { navController.navigate("review") }
                )
            }
            composable("vocabulary") {
                VocabularyListScreen(
                    viewModel = viewModel,
                    onPracticeClick = { navController.navigate("review_vocabulary") }
                )
            }
            composable("profile") {
                ProfileScreen(viewModel = viewModel)
            }
            composable("add_word") {
                AddWordScreen(
                    viewModel = viewModel,
                    onWordSaved = { navController.popBackStack() }
                )
            }
            composable("review") {
                com.example.woordup.ui.screens.ReviewScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    reviewMode = com.example.woordup.ui.screens.ReviewMode.LEARNING
                )
            }
            composable("review_vocabulary") {
                com.example.woordup.ui.screens.ReviewScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    reviewMode = com.example.woordup.ui.screens.ReviewMode.VOCABULARY
                )
            }
        }
    }
}
