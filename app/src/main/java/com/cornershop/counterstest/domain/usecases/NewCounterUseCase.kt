package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow

typealias CreateNewCounterBaseUseCase = BaseUseCase<Counters, Flow<List<Counters>>>

class CreateNewCounterUseCase(
    private val countersRepository: ICountersRepository
) : CreateNewCounterBaseUseCase {

    override suspend fun invoke(params: Counters) = countersRepository.createNewCounter(params)

}

