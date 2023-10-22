package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.repositories.CountryRepository

@Suppress("UNCHECKED_CAST")
class CountryDetailsViewModelFactory(
    private val countryId: Int,
    private val repository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryDetailsViewModel::class.java)) {
            return CountryDetailsViewModel(countryId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}