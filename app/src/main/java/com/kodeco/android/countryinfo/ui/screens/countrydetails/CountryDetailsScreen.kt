package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CountryDetailsScreen(viewModel: CountryDetailsViewModel, onNavigateUp: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val verticalScrollState = rememberScrollState()

    val typography = MaterialTheme.typography
    val h4Style = typography.headlineLarge
    val h6Style = typography.headlineSmall

    val isDarkTheme = isSystemInDarkTheme()
    val contentColor = contentColorFor(MaterialTheme.colorScheme.surface)
    val backgroundColor = MaterialTheme.colorScheme.background

    when (uiState) {
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UIState.Success -> {
            val country = (uiState as UIState.Success).country
            Box(
                modifier = Modifier.fillMaxSize().background(backgroundColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(verticalScrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Existing UI code for displaying country details
                    IconButton(
                        onClick = {
                            viewModel.incrementBack()
                            onNavigateUp()
                        },
                        modifier = Modifier.size(96.dp).padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = contentColor
                        )
                    }

                    val imagePainter = rememberAsyncImagePainter(model = country.flagUrl)
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp).clip(MaterialTheme.shapes.medium).background(Color.Black).padding(1.dp),
                        contentScale = ContentScale.Crop
                    )

                    val textColor = if (isDarkTheme) Color.White else Color.Black

                    Text(
                        text = "Country: ${country.name.common}",
                        modifier = Modifier.padding(16.dp),
                        style = h4Style,
                        color = textColor
                    )

                    if (country.capital.isNullOrEmpty()) {
                        Text(
                            text = "Has no capital!",
                            modifier = Modifier.padding(16.dp),
                            style = h6Style,
                            color = textColor
                        )
                    } else {
                        Text(
                            text = "Capital: ${country.capital[0]}",
                            modifier = Modifier.padding(16.dp),
                            style = h6Style,
                            color = textColor
                        )
                    }

                    Text(
                        text = "Area: ${country.area} km²",
                        modifier = Modifier.padding(16.dp),
                        style = h6Style,
                        color = textColor
                    )

                    Text(
                        text = "Population: ${country.population}",
                        modifier = Modifier.padding(16.dp),
                        style = h6Style,
                        color = textColor
                    )
                }
            }
        }
        is UIState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Failed to load data. Please retry.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.onRefresh(countryId = 1) }) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }
}
