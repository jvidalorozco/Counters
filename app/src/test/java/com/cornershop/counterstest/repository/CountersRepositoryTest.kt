package com.cornershop.counterstest.repository

import com.cornershop.counterstest.BaseTest
import com.cornershop.counterstest.data.CountersRepository
import com.cornershop.counterstest.domain.repository.ICountersRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CountersRepositoryTest : BaseTest() {

    private lateinit var countersRepository: ICountersRepository

    @Before
    override fun setup() {
        super.setup()
        countersRepository = CountersRepository(countersApiService)
    }

    @Test
    fun `given when executed then return list of results`() {
        runBlocking {
            val results = countersRepository.getAllCounters()
            results.collect { Truth.assertThat(it).isNotEmpty() }
        }
    }


}