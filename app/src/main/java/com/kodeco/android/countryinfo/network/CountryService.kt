package com.kodeco.android.countryinfo.network

import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.networking.adapters.WrappedCountryList
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    @WrappedCountryList
    suspend fun getAllCountries(): Response<List<Country>>

}
