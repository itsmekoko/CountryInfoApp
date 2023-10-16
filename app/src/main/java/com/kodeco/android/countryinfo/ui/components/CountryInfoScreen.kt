package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.network.fetchCountriesFlow

sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(service: CountryService) {
    var state: CountryInfoState by remember { mutableStateOf(CountryInfoState.Loading) }

    Surface {
        Column {
            // First Row - Taps and Backs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display tap count
                val tapCount by AppFlows.tapFlow.collectAsState(initial = 0)
                androidx.compose.material.Text(
                    text = "Taps: $tapCount",
                    color = contentColorFor(MaterialTheme.colors.primary)
                )

                // Display back count
                val backCount by AppFlows.backFlow.collectAsState(initial = 0)
                androidx.compose.material.Text(
                    text = "Backs: $backCount",
                    color = contentColorFor(MaterialTheme.colors.primary)
                )
            }

            // Second Row - App Uptime and Countries Loaded
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display app uptime
                val uptime by AppFlows.counterFlow.collectAsState(initial = 0)
                androidx.compose.material.Text(
                    text = "App Uptime: $uptime seconds",
                    color = contentColorFor(MaterialTheme.colors.primary)
                )

                // Display countries loaded
                val countriesLoaded by AppFlows.countriesLoadedFlow.collectAsState(initial = 0)
                androidx.compose.material.Text(
                    text = "Countries Loaded: $countriesLoaded",
                    color = contentColorFor(MaterialTheme.colors.primary)
                )
            }

            // Refresh button
            Button(
                onClick = { state = CountryInfoState.Loading },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                androidx.compose.material.Text(
                    text = "Refresh",
                    color = MaterialTheme.colors.onPrimary
                ) // This color is for text on buttons
            }

            when (val curState = state) {
                is CountryInfoState.Loading -> LoadingScreen()
                is CountryInfoState.Success -> CountryInfoList(curState.countries)
                is CountryInfoState.Error -> CountryErrorScreen(curState.error) {
                    state = CountryInfoState.Loading
                }
            }
        }

        if (state is CountryInfoState.Loading) {
            LaunchedEffect(key1 = "fetch-countries") {
                fetchCountriesFlow(service).collect { newState ->
                    state = newState
                }
            }
        }
    }
}
