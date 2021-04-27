package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow

typealias IncrementBaseUseCase = BaseUseCase<Counters, Flow<List<Counters>>>

class IncrementUseCase(
    private val countersRepository: ICountersRepository
) : DecrementBaseUseCase {

    override suspend fun invoke(params: Counters) = countersRepository.incrementCounter(params)

}

