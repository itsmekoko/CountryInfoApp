package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.SharedRepository
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
        data class Error(val error: Throwable) : CountryInfoState()
    }

    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState

    val tapCount = mutableIntStateOf(0)
    val backCount = SharedRepository.backCounter
    val uptimeCounter = mutableIntStateOf(0)

    init {
        fetchCountries()
        startUptimeCounter()
    }

    private fun startUptimeCounter() {
        viewModelScope.launch {
            while (true) {
                delay(1000)  // Delay for 1 second
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
                val countries = repository.fetchCountries().first()
                _uiState.emit(CountryInfoState.Success(countries))
            } catch (e: Exception) {
                _uiState.emit(CountryInfoState.Error(e))
            }
        }
    }

    fun incrementTap() {
        tapCount.intValue++
    }


}
