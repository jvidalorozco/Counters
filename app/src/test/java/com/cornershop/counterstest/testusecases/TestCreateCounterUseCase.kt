package com.cornershop.counterstest.testusecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.usecases.CreateNewCounterBaseUseCase
import com.cornershop.counterstest.utils.Data
import com.cornershop.counterstest.utils.UiState
import kotlinx.coroutines.flow.Flow


class TestCreateCounterUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Counters>,Counters>(uiState), CreateNewCounterBaseUseCase {

    override suspend fun invoke(params: Counters): Flow<List<Counters>> = execute(params)

    override fun getValue(params: Counters): List<Counters> = Data.counters

}