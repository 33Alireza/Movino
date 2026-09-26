package com.example.movino.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movino.core.common.UiState
import com.example.movino.core.navigation.Detail
import com.example.movino.data.api.MoviesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesApi: MoviesApi, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    private val movieId = savedStateHandle.toRoute<Detail>().id

    init {
        getMovie()
    }

    fun getMovie() {
        _uiState.update {
            it.copy(
                movie = UiState.Loading
            )
        }
        viewModelScope.launch {
            try {
                val response = moviesApi.getMovieById(movieId)
                _uiState.update {
                    it.copy(
                        movie = UiState.Success(response)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        movie = UiState.Error(e.message ?: "Failed to get movie")
                    )
                }
            }
        }
    }
}