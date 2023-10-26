package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.repositories.CountryRepository

@Suppress("UNCHECKED_CAST")
class CountryInfoViewModelFactory(
    private val repository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryInfoViewModel::class.java)) {
            return CountryInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}