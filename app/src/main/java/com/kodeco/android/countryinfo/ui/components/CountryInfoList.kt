package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kodeco.android.countryinfo.data.Country

// TODO fill out CountryInfoList
@Composable
fun CountryInfoList(countries: List<Country>, navController: NavController) {
    LazyColumn {
        items(countries) { country ->
            CountryInfoRow(country, navController) // Your existing CountryInfoRow composable
            HorizontalDivider(thickness = 1.dp, color = Color.Black) // Adds a black separation line
        }
    }
}


// TODO fill out the preview.

