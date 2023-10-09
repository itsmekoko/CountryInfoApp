package com.kodeco.android.countryinfo.ui.components

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
object AppFlows {
    private val _tapFlow = MutableSharedFlow<Int>(replay = 1)
    val tapFlow: SharedFlow<Int> = _tapFlow

    private val _backFlow = MutableSharedFlow<Int>(replay = 1)
    val backFlow: SharedFlow<Int> = _backFlow

    private val _counterFlow = MutableSharedFlow<Int>(replay = 1)
    val counterFlow: SharedFlow<Int> = _counterFlow

    private val _countriesLoadedFlow = MutableSharedFlow<Int>(replay = 1)
    val countriesLoadedFlow: SharedFlow<Int> = _countriesLoadedFlow

    val combinedFlow: Flow<AppInfo> = combine(_tapFlow, _backFlow, _countriesLoadedFlow) { taps, backs, countries ->
        AppInfo(taps, backs, countries)
    }

    data class AppInfo(val taps: Int, val backs: Int, val countriesLoaded: Int)

    fun tap() {
        val currentTapCount = _tapFlow.replayCache.firstOrNull() ?: 0
        _tapFlow.tryEmit(currentTapCount + 1)
    }

    fun tapBack() {
        val currentBackCount = _backFlow.replayCache.firstOrNull() ?: 0
        _backFlow.tryEmit(currentBackCount + 1)
    }

    fun updateCountriesLoaded(count: Int) {
        _countriesLoadedFlow.tryEmit(count)
    }

    init {
        GlobalScope.launch {
            while (true) {
                delay(1000L)
                val currentCounter = _counterFlow.replayCache.firstOrNull() ?: 0
                _counterFlow.tryEmit(currentCounter + 1)
            }
        }
    }
}
