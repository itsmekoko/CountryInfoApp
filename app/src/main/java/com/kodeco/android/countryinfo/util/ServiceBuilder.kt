package com.kodeco.android.countryinfo.util

import com.kodeco.android.countryinfo.network.CountryService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

    object ServiceBuilder {
        private const val BASE_URL = "https://restcountries.com/v3.1/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val countryService: CountryService = retrofit.create(CountryService::class.java)
    }

