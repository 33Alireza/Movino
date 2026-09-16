package com.example.movino.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movino.core.navigation.Detail
import com.example.movino.data.api.MoviesApi
import com.example.movino.data.dto.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesApi: MoviesApi, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movie = MutableStateFlow<MovieDto?>(null)
    val movie = _movie.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    var isLoading = MutableStateFlow(true)
        private set

    private var movieId: Int = 0

    init {
        movieId = savedStateHandle.toRoute<Detail>().id
        getMovie()
    }

    fun getMovie() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = moviesApi.getMovieById(movieId)
                _movie.value = response
            } catch (e: Exception) {
                _event.emit(e.message ?: "Server Error")
            } finally {
                isLoading.value = false
            }
        }
    }
}