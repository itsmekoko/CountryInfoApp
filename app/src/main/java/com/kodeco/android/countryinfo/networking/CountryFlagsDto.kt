package com.kodeco.android.countryinfo.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryFlagsDto(
    @Json(name = "png")
    val png: String,

    @Json(name = "svg")
    val svg: String
)