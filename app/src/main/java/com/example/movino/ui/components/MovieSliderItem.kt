package com.example.movino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movino.data.dto.MovieData
import com.example.movino.ui.theme.MovinoTheme

@Composable
fun MovieSliderItem(
    modifier: Modifier = Modifier,
    movie: MovieData,
    onMovieClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .width(265.dp)
            .height(148.dp)
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ), onClick = onMovieClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = movie.images?.last(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    blendMode = BlendMode.SrcAtop
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.weight(60f),
                    text = movie.title,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(40f))
            }
        }
    }
}

@Preview
@Composable
private fun MovieSliderItemPreview() {
    MovinoTheme {
        MovieSliderItem(
            movie = MovieData(
                1,
                "The Shawshank Redemption",
                "http://moviesapi.ir/images/tt0111161_screenshot1.jpg",
                genres = listOf(
                    "Crime", "Drama"
                ),
                images = listOf(
                    ""
                ),
                year = "2008",
                country = "USA, CANADA",
                imdbRating = "9.8",
            ),
            onMovieClick = {},
            modifier = Modifier,
        )
    }
}