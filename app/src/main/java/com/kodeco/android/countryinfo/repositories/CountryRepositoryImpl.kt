package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

class CountryRepositoryImpl(private val service: CountryService) : CountryRepository {
    private var countries: List<Country>? = null
    private val repoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        fetchCountries().launchIn(repoScope)
    }

    override fun triggerFetchCountries(): Flow<List<Country>> {
        return fetchCountries()
    }

    override fun fetchCountries(): Flow<List<Country>> = flow {
        val countriesResponse = service.getAllCountries()
        if (countriesResponse.isSuccessful) {
            val fetchedCountries = countriesResponse.body()!!
            countries = fetchedCountries
            emit(fetchedCountries)
        } else {
            throw Exception("Request failed: ${countriesResponse.message()}")
        }
    }

    override fun getCountry(id: Int): Country? {
        return countries?.getOrNull(id)
    }

}
