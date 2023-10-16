package com.kodeco.android.countryinfo.network

import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.ui.components.AppFlows
import com.kodeco.android.countryinfo.ui.components.CountryInfoState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    suspend fun getAllCountries(): Response<List<Country>>
}

fun fetchCountriesFlow(service: CountryService): Flow<CountryInfoState> = flow {
    emit(CountryInfoState.Loading)
    delay(1_000)
    try {
        val countriesResponse = service.getAllCountries()
        if (countriesResponse.isSuccessful) {
            val countries = countriesResponse.body()!!
            AppFlows.updateCountriesLoaded(countries.size)
            emit(CountryInfoState.Success(countries))  // Emit success state with data
        } else {
            emit(CountryInfoState.Error(Throwable("Request failed: ${countriesResponse.message()}")))  // Emit error state
        }
    } catch (exception: Exception) {
        emit(CountryInfoState.Error(exception))  // Emit error state
    }
}

