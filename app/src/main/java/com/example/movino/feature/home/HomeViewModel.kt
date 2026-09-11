package com.example.movino.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movino.data.api.GenresApi
import com.example.movino.data.api.MoviesApi
import com.example.movino.data.dto.Genre
import com.example.movino.data.dto.MovieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesApi: MoviesApi,
    private val genresApi: GenresApi,
) : ViewModel() {
    private var _movies = MutableStateFlow<List<MovieData>?>(null)
    val movies = _movies.asStateFlow()

    private var _genres = MutableStateFlow<List<Genre>?>(null)
    val genres = _genres.asStateFlow()

    private var _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    var isLoading = MutableStateFlow(false)
        private set

    var selectedGenre = MutableStateFlow(Genre(-1, "All"))
        private set


    init {
        refreshState()
    }

    suspend fun onError(message: String) {
        _event.emit(message)
    }

    suspend fun getMovies() {
        if (!isLoading.value) {
            isLoading.value = true
            val moviesResponse: List<MovieData> = if (selectedGenre.value.name == "All") {
                moviesApi.getMovies().data
            } else {
                moviesApi.getMoviesByGenreId(selectedGenre.value.id).data
            }
            _movies.value = moviesResponse
            isLoading.value = false
        }
    }

    suspend fun getGenres() {
        if (!isLoading.value) {
            isLoading.value = true
            val genresResponse = genresApi.getGenres()
            _genres.value = listOf(Genre(-1, "All")) + genresResponse
            isLoading.value = false
        }

    }

    fun refreshState() {
        if (!isLoading.value) {
            viewModelScope.launch {
                try {
                    getGenres()
                    getMovies()
                } catch (e: Exception) {
                    onError(e.message ?: "Unknown Error")
                }
            }
        }
    }

    fun onGenreChange(newGenre: Genre) {
        if (!isLoading.value) {
            viewModelScope.launch {
                try {
                    selectedGenre.value = newGenre
                    getMovies()
                } catch (e: Exception) {
                    onError(e.message ?: "Unknown error")
                }
            }
        }
    }
}