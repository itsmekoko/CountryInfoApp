package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryDetailsViewModel(
    countryId: Int,
    private val repository: CountryRepository
) : ViewModel() {

    private val _country = MutableStateFlow<Country?>(null)
    val country = _country.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getCountry(countryId)
            _country.emit(result)
        }
    }

    fun incrementBack() {
        SharedRepository.incrementBackCounter()
    }

}