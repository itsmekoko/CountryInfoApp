package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.networking.adapters.CountryAdapter
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.nav.CountryInfoNavHost
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moshi = Moshi.Builder()
            .add(CountryAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


        val service: CountryService = retrofit.create(CountryService::class.java)
        val repository = CountryRepositoryImpl(service, applicationContext)

        setContent {
            MyApplicationTheme {
                CountryInfoNavHost(repository = repository)
            }
        }
    }
}



