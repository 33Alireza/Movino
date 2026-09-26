package com.example.movino.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movino.core.common.UiState
import com.example.movino.data.api.GenresApi
import com.example.movino.data.api.MoviesApi
import com.example.movino.data.dto.GenreDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesApi: MoviesApi,
    private val genresApi: GenresApi,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var moviesJob: Job? = null

    private var genresJob: Job? = null

    init {
        refreshState()
    }

    private fun getMovies() {
        moviesJob?.cancel()
        _uiState.update {
            it.copy(movies = UiState.Loading)
        }
        moviesJob = viewModelScope.launch {
            try {
                val moviesResponse = if (_uiState.value.selectedGenre.id == -1) {
                    moviesApi.getMovies().data
                } else {
                    moviesApi.getMoviesByGenreId(_uiState.value.selectedGenre.id).data
                }
                _uiState.update {
                    it.copy(movies = UiState.Success(moviesResponse))
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _uiState.update {
                    it.copy(
                        movies = UiState.Error(
                            e.message ?: "Failed to load movies"
                        )
                    )
                }
            }
        }
    }

    private fun getGenres() {
        genresJob?.cancel()
        _uiState.update {
            it.copy(genres = UiState.Loading)
        }
        genresJob = viewModelScope.launch {
            try {
                val genresResponse = genresApi.getGenres()
                _uiState.update {
                    it.copy(
                        genres = UiState.Success(
                            listOf(GenreDto(-1, "All")) + genresResponse
                        )
                    )
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _uiState.update {
                    it.copy(
                        genres = UiState.Error(
                            e.message ?: "Failed to load genres"
                        )
                    )
                }
            }
        }
    }

    fun refreshState() {
        getGenres()
        getMovies()
    }

    fun onGenreChange(newGenre: GenreDto) {
        _uiState.update {
            it.copy(
                selectedGenre = newGenre
            )
        }
        getMovies()
    }
}