package com.example.movino.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes

@Serializable
data object Splash : AppRoutes

@Serializable
data object Home : AppRoutes

@Serializable
data object Search : AppRoutes

@Serializable
data class Detail(
    val id: Int
) : AppRoutes