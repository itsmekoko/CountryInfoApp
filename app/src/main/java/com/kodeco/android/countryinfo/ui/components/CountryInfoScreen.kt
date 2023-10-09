package com.kodeco.android.countryinfo.ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.sample.sampleCountries
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()

    @Composable

    fun CountryInfoScreen(
        service: CountryService,
    ) {
        var state: CountryInfoState by remember { mutableStateOf(Loading) }

        Surface {
            when (val curState = state) {
                is Loading -> Loading()
                is Success -> CountryInfoList(curState.countries)
                is Error -> CountryErrorScreen(curState.error) {
                    state = Loading
                }

                else -> {}
            }
        }

        if (state == Loading) {
            LaunchedEffect(key1 = "fetch-countries") {
                launch {
                    delay(1_000)
                    state = try {
                        val countriesResponse = service.getAllCountries()

                        if (countriesResponse.isSuccessful) {
                            Success(countriesResponse.body()!!)
                        } else {
                            Error(Throwable("Request failed: ${countriesResponse.message()}"))
                        }
                    } catch (exception: Exception) {
                        Error(exception)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun CountryInfoScreenPreview() {
        CountryInfoScreen(object : CountryService {
            override suspend fun getAllCountries(): Response<List<Country>> =
                Response.success(sampleCountries)
        })
    }
}