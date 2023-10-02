package com.kodeco.android.countryinfo.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryName(
    val common: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryFlags(
    val png: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlags
) : Parcelable {
    val commonName get() = name.common
}

