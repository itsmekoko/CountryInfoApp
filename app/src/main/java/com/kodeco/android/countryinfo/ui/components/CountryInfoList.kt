package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.sample.sampleCountries

@Composable
fun CountryInfoList(countries: List<Country>) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    selectedCountry?.let { country ->
        CountryDetailsScreen(country) { selectedCountry = null }
    } ?: run {
        LazyColumn {
            items(countries) { country ->
                CountryInfoRow(country) {
                    selectedCountry = country
                }
                HorizontalDivider(thickness = 1.dp, color = Color.Black)  // Updated divider
            }
        }
    }
}


@Preview
@Composable
fun CountryInfoListPreview() {
    CountryInfoList(sampleCountries)
}