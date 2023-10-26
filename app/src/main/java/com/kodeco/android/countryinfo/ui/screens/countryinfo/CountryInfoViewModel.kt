package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryInfoViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState

    var tapCount = mutableIntStateOf(0)
    var backCount = mutableIntStateOf(0)
    var uptimeCounter = mutableIntStateOf(0)

    init {
        fetchCountries()
        startUptimeCounter()
    }

    fun incrementTap() {
        tapCount.intValue++
    }

    fun incrementBack() {
        backCount.intValue++
    }

    fun onRefresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _uiState.value = CountryInfoState.Loading
            try {
                Log.d("CountryInfoVM", "Fetching countries...")
                repository.fetchCountries().collect { countries ->
                    if (countries.isNotEmpty()) {
                        _uiState.value = CountryInfoState.Success(countries)
                    } else {
                        _uiState.value = CountryInfoState.Error(Exception("No countries found"))
                    }
                }
            } catch (e: Exception) {
                _uiState.value = CountryInfoState.Error(e)
                Log.e("CountryInfoVM", "Error fetching countries: ", e)
            }
        }
    }

    private fun startUptimeCounter() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                uptimeCounter.intValue++
            }
        }
    }
}
