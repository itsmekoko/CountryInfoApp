package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.sample.sampleCountry
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun CountryDetailsScreen(country: Country, viewModel: CountryInfoViewModel, onNavigateUp: () -> Unit) {
    val verticalScrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val imagePainter = rememberAsyncImagePainter(model = country.flagUrl)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Black)
                    .padding(1.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Country: ${country.name.common}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h4,
                color = contentColorFor(MaterialTheme.colors.primary)
            )

            if (country.capital.isNullOrEmpty()) {
                Text(
                    text = "Has no capital!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6,
                    color = contentColorFor(MaterialTheme.colors.primary)
                )
            } else {
                Text(
                    text = "Capital: ${country.capital[0]}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6,
                    color = contentColorFor(MaterialTheme.colors.primary)
                )
            }

            Text(
                text = "Area: ${country.area} km²",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
                color = contentColorFor(MaterialTheme.colors.primary)
            )

            Text(
                text = "Population: ${country.population}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
                color = contentColorFor(MaterialTheme.colors.primary)
            )

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .padding(16.dp)
                    .clickable {
                        viewModel.incrementBack()
                        onNavigateUp()
                    },
                tint = contentColorFor(MaterialTheme.colors.primary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsScreenPreview() {
    CountryDetailsScreen(sampleCountry, CountryInfoViewModel(DummyRepository)) {}
}

object DummyRepository : CountryRepository {
    override fun fetchCountries(): Flow<List<Country>> = flowOf(emptyList())

    override fun triggerFetchCountries(): Flow<List<Country>> = flowOf(emptyList())

    override fun getCountry(id: String): Country? = null
}