package com.example.movino.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {}
        composable<Home> {}
        composable<Search> {}
        composable<Detail> {}
    }
}