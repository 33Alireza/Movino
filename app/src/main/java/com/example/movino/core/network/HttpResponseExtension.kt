package com.example.movino.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

suspend inline fun <reified T> bodyOrThrow(response: HttpResponse): T {
    if (!response.status.isSuccess()) {
        throw Exception(response.bodyAsText())
    }
    return response.body<T>()
}