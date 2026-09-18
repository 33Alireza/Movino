package com.example.movino.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movino.R
import com.example.movino.core.common.UiStateEnum
import com.example.movino.data.dto.MovieDataDto
import com.example.movino.ui.components.CustomTopAppBar
import com.example.movino.ui.components.MovieCard
import com.example.movino.ui.components.MovieSearchBar
import com.example.movino.ui.components.SearchResult
import com.example.movino.ui.components.SearchResultError
import com.example.movino.ui.theme.MovinoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToPreviousScreen: () -> Unit,
    navigateToMovieDetailScreen: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val moviesState by viewModel.movies.collectAsStateWithLifecycle()
    val movieName by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                topAppBarTitle = stringResource(R.string.search_screen_title),
                topAppBarNavigationIcon = {
                    IconButton(
                        modifier = Modifier.size(24.dp), onClick = {
                            navigateToPreviousScreen()
                        }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                })
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            item {
                MovieSearchBar(
                    value = movieName,
                    onValueChange = { viewModel.onMovieNameChanged(it) },
                    uiState = uiState
                )
            }
            if (uiState == UiStateEnum.Empty) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchResult(searchResultError = SearchResultError.NotFound)
                    }
                }
            } else if (uiState == UiStateEnum.Exception) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchResult(searchResultError = SearchResultError.BadRequest)
                    }
                }
            }
            items(moviesState) { movie ->
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MovieCard(
                        movie = MovieDataDto(
                            id = movie.id,
                            title = movie.title,
                            poster = movie.poster,
                            year = movie.year,
                            country = movie.country,
                            imdbRating = movie.imdbRating,
                            genres = movie.genres ?: emptyList(),
                            images = movie.images ?: emptyList()
                        ), modifier = Modifier.clickable(
                            onClick = { navigateToMovieDetailScreen(movie.id) })
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    MovinoTheme {
        SearchScreen(navigateToPreviousScreen = {}, navigateToMovieDetailScreen = {})
    }
}