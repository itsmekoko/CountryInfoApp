package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun CountryDetailsScreen(viewModel: CountryDetailsViewModel,  onNavigateUp: () -> Unit) {


    val country by viewModel.country.collectAsState()
    val verticalScrollState = rememberScrollState()

    val typography = androidx.compose.material3.MaterialTheme.typography
    val h4Style = typography.headlineLarge
    val h6Style = typography.headlineSmall

    val isDarkTheme = isSystemInDarkTheme()
    val contentColor = contentColorFor(androidx.compose.material3.MaterialTheme.colorScheme.surface)
    val backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background

    if (country != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(verticalScrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                IconButton(
                    onClick = {
                        viewModel.incrementBack()
                        onNavigateUp()
                    },
                    modifier = Modifier
                        .size(96.dp)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = contentColor
                    )
                }

                val imagePainter = rememberAsyncImagePainter(model = country!!.flagUrl)
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(androidx.compose.material3.MaterialTheme.shapes.medium)
                        .background(Color.Black)
                        .padding(1.dp),
                    contentScale = ContentScale.Crop
                )

                val textColor = if (isDarkTheme) Color.White else Color.Black

                androidx.compose.material3.Text(
                    text = "Country: ${country!!.name.common}",
                    modifier = Modifier.padding(16.dp),
                    style = h4Style,
                    color = textColor
                )

                if (country!!.capital.isNullOrEmpty()) {
                    androidx.compose.material3.Text(
                        text = "Has no capital!",
                        modifier = Modifier.padding(16.dp),
                        style = h6Style,
                        color = textColor
                    )
                } else {
                    androidx.compose.material3.Text(
                        text = "Capital: ${country!!.capital?.get(0)}",
                        modifier = Modifier.padding(16.dp),
                        style = h6Style,
                        color = textColor
                    )
                }

                androidx.compose.material3.Text(
                    text = "Area: ${country!!.area} km²",
                    modifier = Modifier.padding(16.dp),
                    style = h6Style,
                    color = textColor
                )

                androidx.compose.material3.Text(
                    text = "Population: ${country!!.population}",
                    modifier = Modifier.padding(16.dp),
                    style = h6Style,
                    color = textColor
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}