package com.example.movino.feature.search

import androidx.compose.foundation.clickable
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
import com.example.movino.core.common.UiState
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            MovieSearchBar(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onMovieNameChanged(it) },
                isLoading = uiState.movies is UiState.Loading
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                when (val movies = uiState.movies) {
                    is UiState.Success -> {
                        if (movies.data.isEmpty()) {
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
                        } else {
                            items(
                                items = movies.data, key = { movie -> movie.id }) { movie ->
                                MovieCard(
                                    movie = movie,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .clickable(
                                            onClick = { navigateToMovieDetailScreen(movie.id) })
                                )
                            }
                        }
                    }

                    is UiState.Error -> {
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

                    is UiState.Loading -> {}
                    is UiState.Idle -> {}
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