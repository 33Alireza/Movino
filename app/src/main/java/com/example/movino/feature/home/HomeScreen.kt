package com.example.movino.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.movino.ui.components.CustomTopAppBar
import com.example.movino.ui.components.GenreSlider
import com.example.movino.ui.components.MovieCard
import com.example.movino.ui.components.MovieSlider
import com.example.movino.ui.theme.MovinoTheme

@Composable
fun HomeScreen(
    navigateToMovieDetailScreen: (Int) -> Unit,
    navigateToMovieSearchScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val moviesState by viewModel.movies.collectAsStateWithLifecycle()
    val genresState by viewModel.genres.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()

    val actionLabel = stringResource(R.string.snack_bar_action_label)
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(moviesState, genresState) {
        viewModel.event.collect {
            val result = snackBarHostState.showSnackbar(
                message = it, actionLabel = actionLabel, duration = SnackbarDuration.Indefinite
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.refreshState()
            }
        }
    }

    Scaffold(topBar = {
        CustomTopAppBar(
            topAppBarTitle = stringResource(R.string.home_screen_title), topAppBarNavigationIcon = {
                IconButton(
                    modifier = Modifier.size(24.dp), onClick = {
                        navigateToMovieSearchScreen()
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search_bar),
                        contentDescription = null
                    )
                }
            })
    }, snackbarHost = {
        SnackbarHost(snackBarHostState)
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            genresState?.let {
                item {
                    GenreSlider(
                        genresList = genresState ?: emptyList(),
                        selectedGenre = selectedGenre,
                        onCategorySelected = { viewModel.onGenreChange(it) })
                }
            }
            if (isLoading) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                moviesState?.let { movies ->
                    item {
                        MovieSlider(
                            moviesState = moviesState,
                            onMovieClick = { navigateToMovieDetailScreen(it) },
                        )
                    }
                    items(movies) {
                        MovieCard(
                            movie = it, modifier = Modifier.clickable(
                                onClick = { navigateToMovieDetailScreen(it.id) })
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovinoTheme {
        HomeScreen(
            navigateToMovieDetailScreen = {},
            navigateToMovieSearchScreen = {},
        )
    }
}