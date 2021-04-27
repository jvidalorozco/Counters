package com.cornershop.counterstest.di

import com.cornershop.counterstest.commons.NetworkUtils
import com.cornershop.counterstest.data.remote.NetworkConstants
import org.koin.dsl.module

val fakeRepositorykModule = module {

    single { provideRetrofit(okHttpClient = get(), url = NetworkConstants.BASE_URL) }

    single { provideCountersApi(get()) }

    single { provideCountersRepository(api = get()) }

    single { provideOkHttpClient() }
}
