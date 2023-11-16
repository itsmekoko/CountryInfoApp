package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.repositories.SharedRepository
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.LoadingScreen

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
    onCountryRowTap: (Int) -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToPopulation: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val backCountFromSharedRepo = SharedRepository.backCounter.value

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val tapCount = viewModel.tapCount.intValue
                Text(text = "List Taps: $tapCount")
                Text(text = "Detail Backs: $backCountFromSharedRepo")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "App Uptime: ${viewModel.uptimeCounter.intValue} seconds")

                Text(text = "Countries: ${
                    when (state) {
                        is CountryInfoViewModel.CountryInfoState.Success -> (state as CountryInfoViewModel.CountryInfoState.Success).countries.size
                        else -> 0
                    }
                }")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onNavigateToAbout
                ) {
                    Text(text = "About")
                }

                Button(
                    onClick = { viewModel.onRefresh() }
                ) {
                    Text(text = "Refresh")
                }

                Button(
                    onClick = onNavigateToPopulation
                ) {
                    Text(text = "Population")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (state) {
                is CountryInfoViewModel.CountryInfoState.Loading -> LoadingScreen(uptime = viewModel.uptimeCounter.intValue)
                is CountryInfoViewModel.CountryInfoState.Success -> CountryInfoList(
                    countries = (state as CountryInfoViewModel.CountryInfoState.Success).countries,
                    viewModel = viewModel,
                    onCountryClicked = onCountryRowTap
                )
                is CountryInfoViewModel.CountryInfoState.Error -> CountryErrorScreen((state as CountryInfoViewModel.CountryInfoState.Error).message, onRetry = viewModel::onRefresh)
            }
        }
    }
}