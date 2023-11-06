package com.kodeco.android.countryinfo.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryNameDto(
    @Json(name = "common")
    val common: String
)
