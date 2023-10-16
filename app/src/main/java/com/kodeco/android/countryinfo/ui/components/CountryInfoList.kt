package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel

@Composable
fun CountryInfoList(countries: List<Country>, viewModel: CountryInfoViewModel) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    selectedCountry?.let { country ->
        CountryDetailsScreen(country, viewModel) { selectedCountry = null }
    } ?: run {
        LazyColumn {
            items(countries) { country ->
                CountryInfoRow(country, viewModel) {
                    selectedCountry = country
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = androidx.compose.material3.contentColorFor(MaterialTheme.colors.primary)
                )
            }
        }
    }
}