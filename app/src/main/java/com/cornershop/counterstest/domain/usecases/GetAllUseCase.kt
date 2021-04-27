package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow



typealias GetAllCountersBaseUseCase = BaseUseCase<Unit, Flow<List<Counters>>>

class GetAllCountersUseCase(
    private val countersRepository: ICountersRepository
) : GetAllCountersBaseUseCase {

    override suspend fun invoke(params: Unit) = countersRepository.getAllCounters()

}

