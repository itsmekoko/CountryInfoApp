package com.kodeco.android.countryinfo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodeco.android.countryinfo.data.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    @Update
    suspend fun updateCountry(country: Country)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("DELETE FROM country")
    suspend fun clearAllCountries()
}