package com.kodeco.android.countryinfo.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.network.CountryService
import java.io.IOException
import java.util.concurrent.CancellationException

// TODO fill out CountryInfoScreen
@Composable
fun CountryInfoScreen(
    countryService: CountryService,
    navController: NavHostController,
    onCountriesFetched: (List<Country>) -> Unit  // Lambda to receive the fetched list of countries
) {
    var countries by rememberSaveable { mutableStateOf(emptyList<Country>()) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    var isLoading by rememberSaveable { mutableStateOf(true) }  // Initialize isLoading as true

    LaunchedEffect(countryService) {
        // Only fetch countries if the list is empty
        if (countries.isEmpty()) {
            try {
                val response = countryService.getAllCountries()
                if (response.isSuccessful) {
                    val fetchedCountries = response.body() ?: emptyList()
                    countries = fetchedCountries
                    onCountriesFetched(fetchedCountries)  // Invoke the lambda with the fetched list of countries
                } else {
                    // Customize error message based on HTTP error code or use a generic message
                    error = when (response.code()) {
                        404 -> "Countries not found."
                        500 -> "Internal server error. Please try again later."
                        else -> "Unable to load countries."
                    }
                }
            } catch (e: IOException) {
                error = "Network error occurred. Please check your connection."
            } catch (e: CancellationException) {
                // Log the error message for debugging purposes
                Log.d("CountryInfoScreen", "Coroutine cancelled: ${e.message}")
            } catch (e: Exception) {
                error = "An unexpected error occurred: ${e.message}. Please try again."
            } finally {
                isLoading = false  // Set isLoading to false after the network request completes
            }
        }
    }

    when {
        error != null -> CountryErrorScreen(errorMessage = error!!)
        isLoading -> Loading()  // Show Loading composable when isLoading is true
        else -> CountryInfoList(countries, navController)
    }
}




