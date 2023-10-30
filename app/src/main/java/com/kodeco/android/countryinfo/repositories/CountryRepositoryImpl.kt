package com.kodeco.android.countryinfo.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

class CountryRepositoryImpl(private val service: CountryService, private val appContext: Context) : CountryRepository {

    private var countries: List<Country>? = null
    private val repoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        fetchCountries().launchIn(repoScope)
    }

    override fun triggerFetchCountries(): Flow<CountryResult> {
        return fetchCountries()
    }

    override fun fetchCountries(): Flow<CountryResult> {
        return flow {
            if (!isInternetAvailable(appContext)) {
                emit(CountryResult.Error("No internet connection available. Please retry."))
                return@flow
            }

            val countriesResponse = service.getAllCountries()
            if (countriesResponse.isSuccessful) {
                val fetchedCountries = countriesResponse.body()!!
                countries = fetchedCountries
                emit(CountryResult.Success(fetchedCountries))
            } else {
                emit(CountryResult.Error("Error fetching countries. Please retry."))
            }
        }.catch { e: Throwable ->
            emit(CountryResult.Error(e.message ?: "An error occurred. Please retry."))
        }
    }

    override fun getCountry(id: Int): Country? {
        return countries?.getOrNull(id)
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
