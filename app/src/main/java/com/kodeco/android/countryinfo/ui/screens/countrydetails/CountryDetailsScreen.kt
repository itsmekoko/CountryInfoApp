package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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

    var scale by remember { mutableFloatStateOf(1f) }
    val animatedScale by animateFloatAsState(targetValue = scale, label = "")

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

                    Image(
                        painter = rememberAsyncImagePainter(model = country.flagUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .scale(animatedScale)
                            .clickable {
                                scale = if (scale == 1f) 1.3f else 1f
                            }
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color.Black)
                            .padding(1.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(32.dp))

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
        }
    }
}
