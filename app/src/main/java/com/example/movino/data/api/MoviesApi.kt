package com.example.movino.data.api

import com.example.movino.core.network.bodyOrThrow
import com.example.movino.data.dto.MovieResponse
import com.example.movino.data.dto.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesApi(val client: HttpClient) {
    suspend fun getMovies(movieName: String = "", page: Int = 1): MoviesResponse {
        try {
            return bodyOrThrow(
                client.get("api/v1/movies") {
                    if (movieName.isNotEmpty()) {
                        parameter("q", movieName)
                    }
                    parameter("page", page)
                })
        } catch (_: Exception) {
            throw Exception("Server Error")
        }
    }

    suspend fun getMoviesByGenreId(genreId: Int, page: Int = 1): MoviesResponse {
        try {
            return bodyOrThrow(client.get("api/v1/genres/$genreId/movies") {
                parameter("page", page)
            })
        } catch (_: Exception) {
            throw Exception("Server Error")
        }
    }

    suspend fun getMovieById(movieId: Int): MovieResponse {
        try {
            return bodyOrThrow(
                client.get("api/v1/movies/$movieId")
            )
        } catch (_: Exception) {
            throw Exception("Server Error")
        }
    }
}