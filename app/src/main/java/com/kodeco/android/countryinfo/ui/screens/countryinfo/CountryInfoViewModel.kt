package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CountryInfoViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    sealed class CountryInfoState {
        data object Loading : CountryInfoState()
        data class Success(val countries: List<Country>) : CountryInfoState()
        data class Error(val message: String) : CountryInfoState()
    }

    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState

    val tapCount = mutableIntStateOf(0)
    val uptimeCounter = mutableIntStateOf(0)

    init {
        fetchCountries()
        startUptimeCounter()
    }

    private fun startUptimeCounter() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                uptimeCounter.intValue++
            }
        }
    }

    fun onRefresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _uiState.emit(CountryInfoState.Loading)
            try {
                when (val result = repository.fetchCountries().first()) {
                    is CountryResult.Success -> _uiState.emit(CountryInfoState.Success(result.countries))
                    is CountryResult.Error -> _uiState.emit(CountryInfoState.Error(result.message))
                }
            } catch (e: Exception) {
                _uiState.emit(CountryInfoState.Error("An unexpected error occurred"))
            }
        }
    }

    fun incrementTap() {
        tapCount.intValue++
    }

    fun favorite(country: Country) {
        viewModelScope.launch {
            val updatedCountry = country.copy(isFavorite = !country.isFavorite)

            val currentState = _uiState.value
            if (currentState is CountryInfoState.Success) {
                val updatedCountries = currentState.countries.map {
                    if (it.commonName == country.commonName) updatedCountry else it
                }
                _uiState.emit(CountryInfoState.Success(updatedCountries))
            }
        }
    }

}
