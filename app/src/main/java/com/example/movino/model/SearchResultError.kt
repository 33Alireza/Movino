package com.example.movino.model

import com.example.movino.R

enum class SearchResultError(
    val iconId: Int,
    val title: String,
    val description: String
) {
    NotFound(
        iconId = R.drawable.ic_search_not_found,
        title = "No results found",
        description = "Try a different search term"
    ),
    BadRequest(
        iconId = R.drawable.ic_search_bad_request,
        title = "Something went wrong",
        description = "Please try again."
    ),
}