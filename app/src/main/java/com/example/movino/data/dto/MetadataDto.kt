package com.example.movino.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MetadataDto(
    @SerialName("current_page") val currentPage: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("page_count") val pageCount: Int,
    @SerialName("total_count") val totalCount: Int,
)