package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(uptime: Int) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val shimmerItemHeight = 64.dp + 16.dp

    val numberOfItems = (screenHeight / shimmerItemHeight).toInt()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "App Uptime: $uptime seconds")
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(numberOfItems) {
                    CountryInfoRowShimmer()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    LoadingScreen(uptime = 123)
}

