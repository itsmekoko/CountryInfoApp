package com.kodeco.android.countryinfo.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "country")
data class Country(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // added primary key
    val commonName: String,
    val mainCapital: String,
    val population: Long,
    val area: Float,
    val flagUrl: String,
    val isFavorite: Boolean = false
)



