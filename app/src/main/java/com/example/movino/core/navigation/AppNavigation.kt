package com.example.movino.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movino.feature.detail.DetailScreen
import com.example.movino.feature.home.HomeScreen
import com.example.movino.feature.search.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                navigateToMovieDetailScreen = { navController.navigateSingleTop(Detail(it)) },
                navigateToMovieSearchScreen = { navController.navigateSingleTop(Search) })
        }
        composable<Search> {
            SearchScreen(
                navigateToPreviousScreen = { navController.navigateUpSingleTop() },
                navigateToMovieDetailScreen = { navController.navigateSingleTop(Detail(it)) })
        }
        composable<Detail> {
            DetailScreen(
                navigateToPreviousScreen = { navController.navigateUpSingleTop() })
        }
    }
}