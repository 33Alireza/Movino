package com.example.movino.feature.detail

import com.example.movino.core.common.UiState
import com.example.movino.data.dto.MovieDto

data class DetailUiState(
    val movie: UiState<MovieDto> = UiState.Idle,
)