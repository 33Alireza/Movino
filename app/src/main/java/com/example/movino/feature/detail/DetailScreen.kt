package com.example.movino.feature.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.movino.R
import com.example.movino.core.common.UiState
import com.example.movino.ui.components.CustomTopAppBar
import com.example.movino.ui.components.DotsIndicator
import com.example.movino.ui.theme.MovinoTheme
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DetailScreen(
    navigateToPreviousScreen: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiSate by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val actionLabel = stringResource(R.string.snack_bar_action_label)

    LaunchedEffect(uiSate.movie) {
        if (uiSate.movie is UiState.Error) {
            val message = (uiSate.movie as UiState.Error).message
            val result = snackBarHostState.showSnackbar(
                message = message, actionLabel = actionLabel, duration = SnackbarDuration.Indefinite
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.getMovie()
            }
        }
    }

    val context = LocalContext.current
    fun openIMDBWebpage() {
        val imdbId = (uiSate.movie as UiState.Success).data.imdbId
        val intent = Intent(
            Intent.ACTION_VIEW, "https://www.imdb.com/title/$imdbId/".toUri()
        )
        context.startActivity(intent)
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState) {
            Snackbar(
                snackbarData = it,
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.background,
                actionColor = MaterialTheme.colorScheme.primary,
            )
        }
    }, topBar = {
        CustomTopAppBar(
            topAppBarTitle = stringResource(R.string.detail_screen_title),
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
            when (val movie = uiSate.movie) {
                is UiState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is UiState.Success -> {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val images = movie.data.images
                            var currentImageIndex by remember(images) { mutableIntStateOf(0) }
                            LaunchedEffect(currentImageIndex) {
                                if (images?.isNotEmpty() ?: false) {
                                    while (true) {
                                        delay(5_000.milliseconds)
                                        currentImageIndex = (currentImageIndex + 1) % (images.size)
                                    }
                                }
                            }
                            var totalDrag by remember { mutableFloatStateOf(0f) }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(214.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .pointerInput(images) {
                                        detectHorizontalDragGestures(
                                            onDragEnd = {
                                                images?.let {
                                                    if (abs(totalDrag) > 50f) {
                                                        if (totalDrag > 0) {
                                                            if (currentImageIndex == 0) {
                                                                currentImageIndex = images.lastIndex
                                                            } else {
                                                                currentImageIndex -= 1
                                                            }
                                                        } else {
                                                            currentImageIndex =
                                                                (currentImageIndex + 1) % images.size
                                                        }
                                                    }
                                                }
                                                totalDrag = 0f
                                            }) { _, dragAmount ->
                                            totalDrag += dragAmount
                                        }
                                    }) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = images?.get(currentImageIndex)
                                        ?: R.drawable.default_movie_image,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.default_movie_image)
                                )

                                DotsIndicator(
                                    totalDots = images?.size ?: 0,
                                    selectedIndex = currentImageIndex,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 8.dp)
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = movie.data.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                val textRow = mutableListOf(
                                    movie.data.year,
                                    movie.data.rated,
                                    movie.data.runtime,
                                    movie.data.genres.joinToString(", ")
                                )
                                Text(
                                    text = textRow.joinToString(" . "),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(69.dp)
                                        .height(36.dp)
                                        .clip(shape = MaterialTheme.shapes.extraSmall)
                                        .background(color = MaterialTheme.colorScheme.secondary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable(onClick = { openIMDBWebpage() }),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_imdb),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                        Text(
                                            text = movie.data.imdbRating,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(69.dp)
                                        .height(36.dp)
                                        .clip(shape = MaterialTheme.shapes.extraSmall)
                                        .background(color = MaterialTheme.colorScheme.secondary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_metacritic),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                        Text(
                                            text = movie.data.metaScore,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                        )
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = MaterialTheme.shapes.medium)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = movie.data.plot,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.width(56.dp),
                                    text = stringResource(R.string.detail_screen_director),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Text(
                                    text = movie.data.director,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.width(56.dp),
                                    text = stringResource(R.string.detail_screen_writers),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Text(
                                    text = movie.data.writer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.width(56.dp),
                                    text = stringResource(R.string.detail_screen_actors),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Text(
                                    text = movie.data.actors,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {}
                is UiState.Idle -> {}
            }
        }
    }
}

@Preview
@Composable
private fun DetailViewPreview() {
    MovinoTheme {
        DetailScreen(navigateToPreviousScreen = {})
    }
}