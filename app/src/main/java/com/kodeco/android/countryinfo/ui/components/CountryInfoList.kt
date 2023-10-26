package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel

@Composable
fun CountryInfoList(
    countries: List<Country>,
    viewModel: CountryInfoViewModel,
    onCountryClicked: (Int) -> Unit
) {
    LazyColumn {
        items(countries) { country ->
            CountryInfoRow(country, countries.indexOf(country), viewModel) {
                onCountryClicked(countries.indexOf(country))
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary // Use MaterialTheme.colorScheme.primary
            )
        }
    }
}