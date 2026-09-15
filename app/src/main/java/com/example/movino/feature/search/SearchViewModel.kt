package com.example.movino.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movino.core.common.UiStateEnum
import com.example.movino.data.api.MoviesApi
import com.example.movino.data.dto.MovieDataDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesApi: MoviesApi,
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieDataDto>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(UiStateEnum.Off)
    val uiState = _uiState.asStateFlow()

    init {
        processSearching()
    }

    private fun processSearching() {
        viewModelScope.launch {
            _searchQuery.debounce(300).collectLatest { name ->
                if (name.length > 3) {
                    searchMovies(name)
                } else {
                    _movies.value = emptyList()
                    _uiState.value = UiStateEnum.Off
                }
            }
        }
    }

    private suspend fun searchMovies(name: String) {
        if (_uiState.value == UiStateEnum.Loading) return

        _uiState.value = UiStateEnum.Loading

        try {
            val response = moviesApi.getMovies(movieName = name)

            _movies.value = response.data
            _uiState.value = if (response.data.isEmpty()) UiStateEnum.Empty
            else UiStateEnum.Success

        } catch (_: Exception) {
            _uiState.value = UiStateEnum.Exception
        }
    }

    fun onMovieNameChanged(newMovieName: String) {
        _searchQuery.value = newMovieName
    }
}