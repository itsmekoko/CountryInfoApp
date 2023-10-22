package com.kodeco.android.countryinfo.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.population.CountriesByPopulationScreen

@Composable
fun CountryInfoNavHost(repository: CountryRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val viewModel: CountryInfoViewModel = viewModel(factory = CountryInfoViewModelFactory(repository))
            CountryInfoScreen(
                viewModel = viewModel,
                onCountryRowTap = { countryIndex ->
                    navController.navigate("details/$countryIndex")
                },
                onNavigateToAbout = {
                    navController.navigate("about")
                },
                onNavigateToPopulation = {
                    navController.navigate("countriesByPopulation")
                }
            )
        }

        composable("details/{countryIndex}") { backStackEntry ->
            val countryIndexString = backStackEntry.arguments?.getString("countryIndex")
            if (countryIndexString != null) {
                val countryIndex = countryIndexString.toInt()
                val detailsViewModel: CountryDetailsViewModel = viewModel(
                    factory = CountryDetailsViewModelFactory(countryIndex, repository)
                )
                CountryDetailsScreen(
                    viewModel = detailsViewModel
                ) { navController.popBackStack() }
            }
        }

        composable("about") {
            AboutScreen(navController = navController)
        }

        composable("countriesByPopulation") {

            CountriesByPopulationScreen(navController = navController, repository = repository)

        }
    }
}