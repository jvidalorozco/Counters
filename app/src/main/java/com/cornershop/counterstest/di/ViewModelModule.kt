package com.cornershop.counterstest.di

import com.cornershop.counterstest.presentation.viewmodels.MainScreenViewModel
import com.cornershop.counterstest.presentation.viewmodels.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        MainScreenViewModel(
            getAllCounters = get(named("counters")),
            createCounter = get(named("newCounter")),
            decrementCounters = get(named("decrement")),
            incrementCounters = get(named("increment")),
            deleteCounters = get(named("delete"))
        )

    }

    viewModel {
        SharedViewModel()
    }




}