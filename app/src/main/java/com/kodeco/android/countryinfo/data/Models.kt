package com.kodeco.android.countryinfo.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryName(
    val
    common: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class CountryFlags(
    val png: String, val svg: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Float,
    val flags: CountryFlags,
    val isFavorite: Boolean = false
) : Parcelable {
    val commonName get() = name.common
    @IgnoredOnParcel
    val flagUrl = flags.png
}



