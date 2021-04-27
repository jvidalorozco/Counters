package com.cornershop.counterstest.di

import androidx.room.Room
import com.cornershop.counterstest.data.CountersRepository
import com.cornershop.counterstest.data.local.CountersDao
import com.cornershop.counterstest.data.local.CountersDatabase
import com.cornershop.counterstest.domain.repository.ICountersRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataSourceModule = module {


    single {
        Room
            .databaseBuilder(androidContext(), CountersDatabase::class.java, "app_db")
            .build()
    }

    single { provideCountersDao(db = get()) }


}

internal fun provideCountersDao(db: CountersDatabase): CountersDao = db.countersDao()


/*
val databaseModule = module {

    fun provideDatabase(application: Application): CountriesDatabase {
        return Room.databaseBuilder(application, CountriesDatabase::class.java, "countries")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: CountriesDatabase): CountriesDao {
        return  database.countriesDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}*/
