package com.kodeco.android.countryinfo.sample

import com.kodeco.android.countryinfo.data.Country
import com.kodeco.android.countryinfo.data.CountryFlags
import com.kodeco.android.countryinfo.data.CountryName

val sampleCountries = listOf(
    Country(
        name = CountryName(common = "Argentina"),
        capital = listOf("Buenos Aires"),
        population = 328_239_523,
        area = 9_833_520f,
        flags = CountryFlags(png = "", svg = ""),
    ),
    Country(
        name = CountryName(common = "Germany"),
        capital = listOf("Berlin"),
        population = 37_742_154,
        area = 9_984_670f,
        flags = CountryFlags(png = "", svg = ""),
    ),
    Country(
        name = CountryName(common = "Romania"),
        capital = listOf("Bucharest"),
        population = 126_014_024,
        area = 1_964_375f,
        flags = CountryFlags(png = "", svg = ""),
    ),
    Country(
        name = CountryName(common = "Indonesia"),
        capital = listOf("Jakarta"),
        population = 83_517_045,
        area = 137_847f,
        flags = CountryFlags(png = "", svg = ""),
    ),
)

val sampleCountry = Country(
    name = CountryName(common = "Spain"),
    capital = listOf("Madrid"),
    population = 328_239_523,
    area = 9_833_520f,
    flags = CountryFlags(png = "", svg = ""),
)
