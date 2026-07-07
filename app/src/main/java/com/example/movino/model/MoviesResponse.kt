package com.example.movino.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val data: List<MovieData>,
    val metadata: Metadata,
)

@Serializable
data class MovieData(
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val country: String,
    @SerialName("imdb_rating") val imdbRating: String,
    val genres: List<String>? = null,
    val images: List<String>? = null,
)

@Serializable
data class Metadata(
    @SerialName("current_page") val currentPage: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("page_count") val pageCount: Int,
    @SerialName("total_count") val totalCount: Int,
)