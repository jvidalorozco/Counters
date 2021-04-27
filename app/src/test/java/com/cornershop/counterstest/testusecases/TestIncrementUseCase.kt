package com.cornershop.counterstest.testusecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.usecases.IncrementBaseUseCase
import com.cornershop.counterstest.domain.usecases.IncrementUseCase
import com.cornershop.counterstest.utils.Data
import com.cornershop.counterstest.utils.UiState
import kotlinx.coroutines.flow.Flow

class TestIncrementUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Counters>, Counters>(uiState), IncrementBaseUseCase {

    override suspend fun invoke(params: Counters): Flow<List<Counters>> = execute(params)

    override fun getValue(params: Counters): List<Counters> = Data.counters

}