package com.cornershop.counterstest.presentation.states

import com.cornershop.counterstest.domain.models.Counters


data class MainScreenViewStates(
    val isLoading: Boolean,
    val error: Error?,
    val isSearching: Boolean,
    val isIncrementOrDecrement: Boolean,
    val isDelete: Boolean,
    val countersResult: List<Counters>?
)