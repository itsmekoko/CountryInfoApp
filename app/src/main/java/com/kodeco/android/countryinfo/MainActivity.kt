package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.components.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import com.kodeco.android.countryinfo.util.ServiceBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countryService = ServiceBuilder.countryService // Initialize your Retrofit service here

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var countries by remember { mutableStateOf(emptyList<Country>()) }

                NavHost(navController, startDestination = "countryList") {
                    composable("countryList") {
                        CountryInfoScreen(countryService, navController) { fetchedCountries ->
                            countries = fetchedCountries
                        }
                    }
                    composable("countryDetails/{commonName}") { backStackEntry ->
                        CountryDetailsScreen(backStackEntry, countries, navController)
                    }
                }
            }
        }
    }
}



