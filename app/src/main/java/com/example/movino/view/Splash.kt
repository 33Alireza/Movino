package com.example.movino.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movino.R
import com.example.movino.ui.theme.LightOnPrimary
import com.example.movino.ui.theme.LightPrimary
import com.example.movino.ui.theme.MovinoTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToHomeScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500L)
        navigateToHomeScreen()
    }

    MovinoTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(LightPrimary),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_splash_screen),
                    contentDescription = stringResource(R.string.splash_view_icon_content_description),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    color = LightOnPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun SplashViewPreview() {
    MovinoTheme {
        SplashScreen(navigateToHomeScreen = {})
    }
}