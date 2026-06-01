package com.example.movino.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarTitle: String,
    topAppBarNavigationIcon: @Composable () -> Unit,
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ), title = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                topAppBarNavigationIcon()
                Text(
                    text = topAppBarTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = {}, enabled = false) {
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        })

}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar(
        topAppBarTitle = stringResource(R.string.home_screen_title), topAppBarNavigationIcon = {
            IconButton(onClick = { /* Button logic */ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigation_icon_content_description)
                )
            }
        })
}