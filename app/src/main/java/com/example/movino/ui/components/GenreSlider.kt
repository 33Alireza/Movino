package com.example.movino.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.data.dto.GenreDto
import com.example.movino.ui.theme.MovinoTheme

@Composable
fun GenreSlider(
    modifier: Modifier = Modifier,
    genresList: List<GenreDto>,
    selectedGenre: GenreDto,
    onCategorySelected: (GenreDto) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Genres",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genresList) { genre ->
                val isSelected = selectedGenre == genre
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onCategorySelected(genre)
                    },
                    label = {
                        Text(
                            text = genre.name, style = if (isSelected) {
                                MaterialTheme.typography.labelLarge
                            } else {
                                MaterialTheme.typography.labelMedium
                            }
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    border = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun GenreSliderPreview() {
    MovinoTheme {
        GenreSlider(
            genresList = listOf(
                GenreDto(
                    id = 1, name = "Action"
                ),
                GenreDto(
                    id = 2, name = "Drama"
                ),
                GenreDto(
                    id = 3, name = "Comedy"
                ),
                GenreDto(
                    id = 4, name = "Fantasy"
                ),
            ),
            selectedGenre = GenreDto(
                id = 3, name = "Comedy"
            ),
            onCategorySelected = {},
        )
    }
}