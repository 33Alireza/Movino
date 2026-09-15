package com.example.movino.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieDataDto(
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val country: String,
    @SerialName("imdb_rating") val imdbRating: String,
    val genres: List<String>? = null,
    val images: List<String>? = null,
)