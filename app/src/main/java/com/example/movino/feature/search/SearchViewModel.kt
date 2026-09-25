package com.example.movino.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movino.core.common.UiState
import com.example.movino.data.api.MoviesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesApi: MoviesApi,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        processSearching()
    }

    private fun processSearching() {
        viewModelScope.launch {
            _uiState.map { it.searchQuery }.distinctUntilChanged().debounce(300.milliseconds)
                .collectLatest { name ->
                    if (name.length > 3) {
                        searchMovies(name)
                    } else {
                        _uiState.update {
                            it.copy(
                                movies = UiState.Idle
                            )
                        }
                    }
                }
        }
    }

    private suspend fun searchMovies(name: String) {
        _uiState.update {
            it.copy(
                movies = UiState.Loading
            )
        }

        try {
            val response = moviesApi.getMovies(movieName = name)

            _uiState.update {
                it.copy(
                    movies = UiState.Success(response.data)
                )
            }

        } catch (e: Exception) {
            if (e is CancellationException) throw e

            _uiState.update {
                it.copy(
                    movies = UiState.Error(
                        e.message ?: "Failed to search movies"
                    )
                )
            }
        }
    }

    fun onMovieNameChanged(newMovieName: String) {
        _uiState.update {
            it.copy(searchQuery = newMovieName)
        }
    }
}