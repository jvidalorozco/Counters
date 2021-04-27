package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow

typealias DecrementBaseUseCase = BaseUseCase<Counters, Flow<List<Counters>>>

class DecrementUseCase(
    private val countersRepository: ICountersRepository
) : DecrementBaseUseCase {

    override suspend fun invoke(params: Counters) = countersRepository.decrementCounter(params)

}

