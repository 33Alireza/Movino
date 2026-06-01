package com.example.movino.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movino.model.MovieData
import com.example.movino.ui.theme.MovinoTheme
import com.example.movino.R

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: MovieData,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp)),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(107.dp)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp, bottomStart = 24.dp
                        )
                    )
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                        contentDescription = null,
                    )
                    Text(
                        text = movie.country,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                    )
                    Text(
                        text = movie.year,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_genre),
                        contentDescription = null,
                    )
                    Text(
                        text = "${movie.genres?.joinToString(", ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(24.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .clickable(onClick = {})
                        .background(color = MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${stringResource(R.string.movie_card_imdb_title)} ${movie.imdbRating}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    MovinoTheme {
        MovieCard(
            movie = MovieData(
                id = 1,
                title = "The Dark knight",
                poster = "https://moviesapi.ir/images/tt0468569_poster.jpg",
                year = "2008",
                country = "USA",
                imdbRating = "9.3",
                genres = listOf("Crime", "Drama"),
                images = listOf(
                    "https://moviesapi.ir/images/tt0468569_screenshot1.jpg",
                    "https://moviesapi.ir/images/tt0468569_screenshot2.jpg",
                    "https://moviesapi.ir/images/tt0468569_screenshot3.jpg"
                )
            ),
        )
    }
}