package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.LoadingScreen

sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {
    val state = viewModel.uiState.collectAsState().value

    Surface {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val tapCount = viewModel.tapCount.intValue

                Text(text = "Taps: $tapCount")

                val backCount = viewModel.backCount.intValue
                Text(text = "Backs: $backCount")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "App Uptime: ${viewModel.uptimeCounter.intValue} seconds")

                Text(text = "Countries: ${
                    when (state) {
                        is CountryInfoState.Success -> state.countries.size
                        else -> 0
                    }
                }")

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = viewModel::onRefresh) {
                    Text(text = "Refresh")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // State UI
            when (state) {
                is CountryInfoState.Loading -> LoadingScreen(uptime = viewModel.uptimeCounter.intValue)
                is CountryInfoState.Success -> CountryInfoList(countries = state.countries, viewModel = viewModel)
                is CountryInfoState.Error -> CountryErrorScreen(state.error, onRetry = viewModel::onRefresh)
            }
        }
    }
}
