package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kodeco.android.countryinfo.data.Country

// TODO fill in the CountryDetailsScreen. NOTE this is above-and-beyond the required
//  section of the homework assignment.

@Composable
fun CountryDetailsScreen(navBackStackEntry: NavBackStackEntry, countries: List<Country>, navController: NavController) {
    val commonName = navBackStackEntry.arguments?.getString("commonName")
    val country = countries.find { it.commonName == commonName }

    // Root Column to hold both the Icon and the other details
    Column(
        modifier = Modifier.padding(start = 16.dp) // Padding applied to the whole column
    ) {
        // Back button with the same padding as the Column
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(48.dp)
                .offset(x = (-8).dp)
                .clickable { navController.navigateUp() },
            tint = Color.Black,
        )

        // Displaying the Country Details
        country?.let {
                Text(text = it.commonName, style = MaterialTheme.typography.headlineMedium)

                val aspectRatio = 1.5f
                Image(
                    painter = rememberAsyncImagePainter(model = it.flags.png),
                    contentDescription = null,
                    modifier = Modifier.width(192.dp)
                        .padding(top = 16.dp)
                        .aspectRatio(ratio = aspectRatio)
                        .border(
                            BorderStroke(2.dp, Color.Black),
                            shape = RectangleShape

                        ),
                    contentScale = ContentScale.FillBounds

                )

                Text(
                    text = "Capital: ${it.capital?.joinToString() ?: "N/A"}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Population: ${it.population}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "Area: ${it.area} sq km", style = MaterialTheme.typography.titleLarge)
            } ?: run {
                Text("Country details are not available")
            }
        }
    }

