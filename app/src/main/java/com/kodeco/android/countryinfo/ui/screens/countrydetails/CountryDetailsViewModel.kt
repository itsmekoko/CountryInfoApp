package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UIState {
    data object Loading : UIState()
    data class Success(val country: Country) : UIState()
    data object Error : UIState()
}

class CountryDetailsViewModel(
    initialCountryId: Int,
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private var countryId: Int = initialCountryId

    init {
        fetchCountryData(countryId)
    }

    private fun fetchCountryData(countryId: Int) {
        viewModelScope.launch {
            println("Fetching country data for ID: $countryId")
            val country = repository.getCountry(countryId)
            if (country != null) {
                println("Fetched country data successfully: ${country.commonName}")
                _uiState.emit(UIState.Success(country))
            } else {
                println("Failed to fetch country data for ID: $countryId")
                _uiState.emit(UIState.Error)
            }
        }
    }

    fun incrementBack() {
        SharedRepository.incrementBackCounter()
    }
}




