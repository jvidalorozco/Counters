package com.cornershop.counterstest.domain.repository

import com.cornershop.counterstest.domain.models.Counters
import kotlinx.coroutines.flow.Flow

interface ICountersRepository {

    fun getAllCounters(): Flow<List<Counters>>
    fun createNewCounter(counters: Counters): Flow<List<Counters>>
    fun incrementCounter(counters: Counters): Flow<List<Counters>>
    fun decrementCounter(counters: Counters): Flow<List<Counters>>
    fun deleteCounter(counters: Counters): Flow<List<Counters>>
}