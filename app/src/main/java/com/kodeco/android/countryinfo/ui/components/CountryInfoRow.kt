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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.sample.sampleCountry

@Composable
fun CountryInfoRow(
    country: Country,
    onClick: (Country) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { onClick(country) }),  // Use .clickable() modifier here
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

@Preview
@Composable
fun CountryInfoRowPreview() {
    CountryInfoRow(
        country = sampleCountry,
        onClick = {},
    )
}