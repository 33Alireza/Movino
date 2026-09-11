package com.example.movino.data.api

import com.example.movino.core.network.bodyOrThrow
import com.example.movino.data.dto.Genre
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class GenresApi(private val client: HttpClient) {
    suspend fun getGenres(): List<Genre> {
        try {
            return bodyOrThrow(client.get("api/v1/genres"))
        } catch (_: Exception) {
            throw Exception("Server Error")
        }
    }
}