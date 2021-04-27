package com.cornershop.counterstest.di

import com.cornershop.counterstest.domain.repository.ICountersRepository
import com.cornershop.counterstest.domain.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {

    single(named("counters")) { provideCounters(get()) }
    single(named("newCounter")) { provideCreateNewCounters(get()) }
    single(named("decrement")) { provideDecrementCounters(get()) }
    single(named("increment")) { provideIncrementCounters(get()) }
    single(named("delete")) { provideDeleteCounters(get()) }

}

fun provideCounters(searchRepository: ICountersRepository): GetAllCountersBaseUseCase {
    return GetAllCountersUseCase(searchRepository)
}

fun provideCreateNewCounters(searchRepository: ICountersRepository): CreateNewCounterBaseUseCase {
    return CreateNewCounterUseCase(searchRepository)
}

fun provideIncrementCounters(searchRepository: ICountersRepository): IncrementBaseUseCase {
    return IncrementUseCase(searchRepository)
}

fun provideDecrementCounters(searchRepository: ICountersRepository): DecrementBaseUseCase {
    return DecrementUseCase(searchRepository)
}

fun provideDeleteCounters(searchRepository: ICountersRepository): DeleteBaseUseCase {
    return DeleteUseCase(searchRepository)
}
