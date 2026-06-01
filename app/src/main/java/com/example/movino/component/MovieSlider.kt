package com.example.movino.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.model.MovieData
import com.example.movino.ui.theme.MovinoTheme

@Composable
fun MovieSlider(
    modifier: Modifier = Modifier,
    moviesState: State<List<MovieData>?>,
    onMovieClick: (Int) -> Unit,
    getMoreMovies: suspend () -> Unit,
) {
    val moviesRowListState = rememberLazyListState()
    LaunchedEffect(moviesRowListState) {
        snapshotFlow { moviesRowListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.collect { lastVisibleIndex ->
                moviesState.value?.let {
                    if (lastVisibleIndex == it.size - 1) {
                        getMoreMovies()
                    }
                }
            }
    }

    moviesState.value?.let { movies ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            LazyRow(
                state = moviesRowListState,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                items(movies) { movie ->
                    MovieSliderItem(movie = movie, onMovieClick = { onMovieClick(movie.id) })
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun MovieSliderPreview() {
    MovinoTheme {
        MovieSlider(
            moviesState = mutableStateOf(
            listOf(
                MovieData(
                    1,
                    "The Shawshank Redemption",
                    "http://moviesapi.ir/images/tt0111161_poster.jpg",
                    genres = listOf(
                        "Crime", "Drama"
                    ),
                    images = listOf(
                        "http://moviesapi.ir/images/tt0111161_screenshot1.jpg",
                        "http://moviesapi.ir/images/tt0111161_screenshot2.jpg",
                        "http://moviesapi.ir/images/tt0111161_screenshot3.jpg"
                    ),
                    year = "2008",
                    country = "USA, CANADA",
                    imdbRating = "9.8",
                ), MovieData(
                    2,
                    "The Godfather",
                    "http://moviesapi.ir/images/tt0068646_poster.jpg",
                    genres = listOf(
                        "Crime", "Drama"
                    ),
                    images = listOf(
                        "http://moviesapi.ir/images/tt0068646_screenshot1.jpg",
                        "http://moviesapi.ir/images/tt0068646_screenshot2.jpg",
                        "http://moviesapi.ir/images/tt0068646_screenshot3.jpg"
                    ),
                    year = "2008",
                    country = "USA, CANADA",
                    imdbRating = "9.8",
                )
            )
        ), getMoreMovies = {}, onMovieClick = {
            Log.d(
                "UDP", "movie with id $it clicked"
            )
        })
    }
}