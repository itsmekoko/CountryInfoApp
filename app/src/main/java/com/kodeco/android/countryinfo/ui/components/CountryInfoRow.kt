package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kodeco.android.countryinfo.data.Country

// TODO fill out CountryInfoRow
@Composable
fun CountryInfoRow(country: Country, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate(
                    "countryDetails/${country.commonName}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load and display the country flag
        Image(
            painter = rememberAsyncImagePainter(model = country.flags.png),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = "Country: ${country.commonName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Capital: ${country.capital?.joinToString() ?: "No Capital"}")
        }
    }
}
