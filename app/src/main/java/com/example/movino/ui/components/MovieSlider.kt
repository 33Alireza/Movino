package com.example.movino.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.data.dto.MovieDataDto
import com.example.movino.ui.theme.MovinoTheme

@Composable
fun MovieSlider(
    modifier: Modifier = Modifier,
    moviesState: List<MovieDataDto>?,
    onMovieClick: (Int) -> Unit,
) {

    moviesState?.let { movies ->
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
            moviesState =
                listOf(
                    MovieDataDto(
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
                    ), MovieDataDto(
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
                ), onMovieClick = {
                Log.d(
                    "UDP", "movie with id $it clicked"
                )
            })
    }
}