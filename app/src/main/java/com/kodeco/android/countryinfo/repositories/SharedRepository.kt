package com.kodeco.android.countryinfo.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf

object SharedRepository {
    var backCounter: MutableState<Int> = mutableIntStateOf(0)
        private set

    fun incrementBackCounter() {
        backCounter.value++
    }
}