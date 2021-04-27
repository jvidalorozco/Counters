package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.local.CountersDao
import com.cornershop.counterstest.data.remote.CountersApiService
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


open class CountersRepository  constructor(
        private val countersApi: CountersApiService
) : ICountersRepository{

    override fun getAllCounters(): Flow<List<Counters>> = flow {
        val searchResponse = countersApi.getAllCounters()
        val counters = mutableListOf<Counters>()
        for (counter in searchResponse) {
            counters.add(counter)
        }
        emit(counters)
    }

    override fun createNewCounter(counters: Counters) = flow {
        val searchResponse = countersApi.createNewCounter(counters)
        val counters = mutableListOf<Counters>()
        for (counter in searchResponse) {
            counters.add(counter)
        }
        emit(counters)
    }

    override fun incrementCounter(counters: Counters) = flow {
        val searchResponse = countersApi.incrementCounter(counters)
        val counters = mutableListOf<Counters>()
        for (counter in searchResponse) {
            counters.add(counter)
        }
        emit(counters)
    }

    override fun decrementCounter(counters: Counters)  = flow {
        val searchResponse = countersApi.decrementCounter(counters)
        val counters = mutableListOf<Counters>()
        for (counter in searchResponse) {
            counters.add(counter)
        }
        emit(counters)
    }

    override fun deleteCounter(counters: Counters)  = flow {
        val searchResponse = countersApi.deleteCounter(counters)
        val counters = mutableListOf<Counters>()
        for (counter in searchResponse) {
            counters.add(counter)
        }
        emit(counters)
    }


}