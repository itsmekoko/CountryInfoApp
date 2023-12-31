package com.kodeco.android.countryinfo.ui.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CountryErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Log.e("CountryErrorScreen", "Error: $message")

        // Use material3 Text component
        Text(
            text = "Error: $message",
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        Button(onClick = onRetry) {
            Text(
                text = "Retry",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }
}

@Preview
@Composable
fun CountryErrorScreenPreview() {
    CountryErrorScreen(
        message = "Error message",
        onRetry = {},
    )
}