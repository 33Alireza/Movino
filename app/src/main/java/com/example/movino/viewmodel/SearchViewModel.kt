package com.example.movino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movino.api.MoviesApi
import com.example.movino.model.MovieData
import com.example.movino.model.UiStateEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesApi: MoviesApi
) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieData>>(emptyList())
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

            val filteredMovies = response.data.filter {
                it.title.isBlank() && it.year.isBlank() && it.imdbRating.isNotBlank() && it.poster.isNotBlank()
            }.distinctBy { it.title.lowercase().trim() }

            _movies.value = filteredMovies
            _uiState.value = if (filteredMovies.isEmpty()) UiStateEnum.Empty
            else UiStateEnum.Success
        } catch (_: Exception) {
            _uiState.value = UiStateEnum.Exception
        }
    }

    suspend fun getMoreMovies() {
        if (_uiState.value == UiStateEnum.Loading) return
        if (_movies.value.isEmpty()) return

        _uiState.value = UiStateEnum.Loading

        try {
            val currentMovies = _movies.value
            val newPage = (currentMovies.size / 10) + 1

            val moviesResponse = moviesApi.getMovies(
                movieName = _searchQuery.value, page = newPage
            ).data

            _movies.update { movies ->
                (movies + moviesResponse).distinctBy { it.title }
            }

            _uiState.value = UiStateEnum.Success

        } catch (_: Exception) {
            _uiState.value = UiStateEnum.Exception
        }
    }

    fun onMovieNameChanged(newMovieName: String) {
        _searchQuery.value = newMovieName
    }
}