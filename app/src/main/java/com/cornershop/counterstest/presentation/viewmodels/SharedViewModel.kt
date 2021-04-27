package com.cornershop.counterstest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cornershop.counterstest.commons.ExceptionHandler
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.usecases.CreateNewCounterBaseUseCase
import com.cornershop.counterstest.presentation.states.MainScreenViewStates
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class SharedViewModel : BaseViewModel() {

     var text = MutableLiveData<String>()
     override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)

    }


}