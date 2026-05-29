package com.example.movino.api

import com.example.movino.model.Genre
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class GenresApi(val client: HttpClient) {
    suspend fun getGenres(): List<Genre> {
        try {
            return bodyOrThrow(client.get("api/v1/genres"))
        } catch (_: Exception) {
            throw Exception("Server error")
        }
    }
}