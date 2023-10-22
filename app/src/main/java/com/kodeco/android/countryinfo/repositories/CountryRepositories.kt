package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.data.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun fetchCountries(): Flow<List<Country>>
    fun getCountry(id: Int): Country?
    fun triggerFetchCountries(): Flow<List<Country>>

}