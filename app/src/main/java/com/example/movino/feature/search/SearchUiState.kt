package com.example.movino.feature.search

import com.example.movino.core.common.UiState
import com.example.movino.data.dto.MovieDataDto

data class SearchUiState(
    val movies: UiState<List<MovieDataDto>> = UiState.Idle,
    val searchQuery: String = ""
)