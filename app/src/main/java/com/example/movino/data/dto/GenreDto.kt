package com.example.movino.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int, val name: String
)