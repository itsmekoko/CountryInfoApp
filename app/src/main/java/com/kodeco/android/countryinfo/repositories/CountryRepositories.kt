package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.data.Country
import kotlinx.coroutines.flow.Flow

sealed class CountryResult {
    data class Success(val countries: List<Country>) : CountryResult()
    data class Error(val message: String) : CountryResult()
}

interface CountryRepository {
    fun triggerFetchCountries(): Flow<CountryResult>
    fun fetchCountries(): Flow<CountryResult>
    fun getCountry(id: Int): Country?
}