package com.example.movino.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    val data: List<MovieDataDto>,
    val metadata: MetadataDto,
)