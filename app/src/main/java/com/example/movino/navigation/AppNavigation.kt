package com.example.movino.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movino.view.DetailScreen
import com.example.movino.view.HomeScreen
import com.example.movino.view.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                navigateToMovieDetailScreen = { navController.navigate(Detail(it)) },
                navigateToMovieSearchScreen = { navController.navigate(Search) })
        }
        composable<Search> {
            SearchScreen(
                navigateToPreviousScreen = { navController.navigateUp() },
                navigateToMovieDetailScreen = { navController.navigate(Detail(it)) })
        }
        composable<Detail> {
            DetailScreen(
                navigateToPreviousScreen = { navController.navigateUp() })
        }
    }
}