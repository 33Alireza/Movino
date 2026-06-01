package com.example.movino.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.R
import com.example.movino.model.UiStateEnum

@Composable
fun MovieSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    uiState: UiStateEnum,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 2.dp, end = 8.dp)
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_search_bar),
                contentDescription = stringResource(R.string.movie_search_bar_search_icon_content_description),
                tint = MaterialTheme.colorScheme.onSurface
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.movie_search_bar_search_place_holder_text),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface)
            )
        }
        val infiniteTransition = rememberInfiniteTransition()
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        if (uiState == UiStateEnum.Loading) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation),
                imageVector = ImageVector.vectorResource(R.drawable.ic_search_bar_loading),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.movie_search_bar_loading_icon_content_description),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 1)
@Composable
private fun MovieSearchBarPreview() {
    MovieSearchBar(
        value = "",
        onValueChange = {},
        uiState = UiStateEnum.Loading,
        modifier = Modifier,
    )
}