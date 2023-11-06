package com.kodeco.android.countryinfo.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "name")
    val name: CountryNameDto,

    @Json(name = "capital")
    val capital: List<String>?,

    @Json(name = "population")
    val population: Long,

    @Json(name = "area")
    val area: Float,

    @Json(name = "flags")
    val flags: CountryFlagsDto
)