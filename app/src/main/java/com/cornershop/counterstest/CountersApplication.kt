package com.cornershop.counterstest

import android.app.Application
import com.cornershop.counterstest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountersApplication : Application() {


    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@CountersApplication)
            modules(
                apiModule,
                networkModule,
                viewModelsModule,
                localDataSourceModule,
                repositoryModule,
                useCasesModule,

            )
        }

    }

}
