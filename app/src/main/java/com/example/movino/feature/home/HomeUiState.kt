package com.example.movino.feature.home

import com.example.movino.core.common.UiState
import com.example.movino.data.dto.GenreDto
import com.example.movino.data.dto.MovieDataDto

data class HomeUiState(
    val movies: UiState<List<MovieDataDto>> = UiState.Idle,
    val genres: UiState<List<GenreDto>> = UiState.Idle,
    val selectedGenre: GenreDto = GenreDto(-1, "All")
)