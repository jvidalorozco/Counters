package com.cornershop.counterstest.domain.usecases

import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.repository.ICountersRepository
import kotlinx.coroutines.flow.Flow

typealias DeleteBaseUseCase = BaseUseCase<Counters, Flow<List<Counters>>>

class DeleteUseCase(
    private val countersRepository: ICountersRepository
) : DeleteBaseUseCase {

    override suspend fun invoke(params: Counters) = countersRepository.deleteCounter(params)

}
