package com.cornershop.counterstest.testusecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.usecases.GetAllCountersBaseUseCase
import com.cornershop.counterstest.utils.Data
import com.cornershop.counterstest.utils.UiState
import kotlinx.coroutines.flow.Flow


class TestGetAllUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Counters>,Unit>(uiState), GetAllCountersBaseUseCase {

    override suspend fun invoke(params: Unit): Flow<List<Counters>> = execute(params)

    override fun getValue(params: Unit): List<Counters> = Data.counters

}