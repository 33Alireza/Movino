package com.example.movino.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val country: String,
    val awards: String,
    @SerialName("metascore")
    val metaScore: String,
    @SerialName("imdb_rating")
    val imdbRating: String,
    @SerialName("imdb_votes")
    val imdbVotes: String,
    @SerialName("imdb_id")
    val imdbId: String,
    val type: String,
    val genres: List<String>,
    val images: List<String>? = null,
)