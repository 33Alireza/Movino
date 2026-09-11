package com.example.movino.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes

@Serializable
data object Home : AppRoutes

@Serializable
data object Search : AppRoutes

@Serializable
data class Detail(
    val id: Int
) : AppRoutes