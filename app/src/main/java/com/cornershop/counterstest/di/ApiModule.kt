package com.cornershop.counterstest.di

import com.cornershop.counterstest.data.CountersRepository
import com.cornershop.counterstest.data.remote.CountersApiService
import org.koin.dsl.module
import retrofit2.Retrofit


val apiModule = module {


    single { provideCountersApi(get()) }

}

fun provideCountersApi(retrofit: Retrofit): CountersApiService {
    return retrofit.create(CountersApiService::class.java)
}