package com.example.movino.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun initialization() = HttpClient {
    defaultRequest {
        host = "moviesapi.ir"
        url {
            protocol = URLProtocol.HTTPS
        }
        contentType(ContentType.Application.Json)
    }
    install(Logging) {
        logger = Logger.ANDROID
    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            })
    }
}

suspend inline fun <reified T> bodyOrThrow(response: HttpResponse): T {
    if (!response.status.isSuccess()) {
        throw Exception(response.bodyAsText())
    }
    return response.body<T>()
}