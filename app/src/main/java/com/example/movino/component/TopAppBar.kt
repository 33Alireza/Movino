package com.example.movino.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    topAppBarTitle: String,
    modifier: Modifier = Modifier,
    topAppBarNavigationIcon: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            topAppBarNavigationIcon()
        },
        title = {
            Text(
                text = topAppBarTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_profile),
                    contentDescription = stringResource(R.string.profile_icon_content_description)
                )
            }
        }
    )
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar(
        topAppBarTitle = stringResource(R.string.home_screen_title),
        topAppBarNavigationIcon = {
            IconButton(onClick = { /* Button logic */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_icon_content_description)
                )
            }
        }
    )
}