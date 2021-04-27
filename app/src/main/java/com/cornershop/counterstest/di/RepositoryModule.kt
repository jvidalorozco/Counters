package com.cornershop.counterstest.di

import com.cornershop.counterstest.data.CountersRepository
import com.cornershop.counterstest.data.local.CountersDao
import com.cornershop.counterstest.data.remote.CountersApiService
import com.cornershop.counterstest.domain.repository.ICountersRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { provideCountersRepository(get()) }

}

fun provideCountersRepository(api: CountersApiService): ICountersRepository {
    return CountersRepository(countersApi = api)
}
