package com.cornershop.counterstest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cornershop.counterstest.commons.ExceptionHandler
import com.cornershop.counterstest.commons.idlingresource.EspressoIdlingResource
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.usecases.*
import com.cornershop.counterstest.presentation.states.Error
import com.cornershop.counterstest.presentation.states.MainScreenViewStates

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class MainScreenViewModel constructor(
    private val getAllCounters: GetAllCountersBaseUseCase,
    private val createCounter: CreateNewCounterBaseUseCase,
    private val decrementCounters: IncrementBaseUseCase,
    private val incrementCounters: DecrementBaseUseCase,
    private val deleteCounters : DeleteBaseUseCase

) : BaseViewModel() {

    // region Members

    private var getAllCounterJob: Job? = null
    private var createNewCounterJob: Job? = null
    private var decrementCounterJob: Job? = null
    private var incrementCounterJob: Job? = null
    private var deleteCounterJob: Job? = null


    val countersViewState: LiveData<MainScreenViewStates>
        get() = _countersViewState

    private var _countersViewState = MutableLiveData<MainScreenViewStates>()



    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        onError(message)

    }

    // endregion

    // region Constructors

    init {
        _countersViewState.value =
            MainScreenViewStates(isLoading = true, error = null, countersResult = null, isSearching = false, isIncrementOrDecrement = false, isDelete = false)
        loadCounters()
    }

    // endregion

    // region Android API

    override fun onCleared() {
        super.onCleared()
        getAllCounterJob?.cancel()
        createNewCounterJob?.cancel()
        incrementCounterJob?.cancel()
        decrementCounterJob?.cancel()
        deleteCounterJob?.cancel()

    }

    // endregion

    // region Public API

    // endregion

    // region Private API

    private fun onLoading() {
        _countersViewState.value = _countersViewState.value?.copy(isLoading = true, isSearching = false)
    }

    fun onSearch(searchText: String){


        _countersViewState.value =
            _countersViewState.value?.copy(isLoading = false, error = null, countersResult = countersViewState.value?.countersResult?.filter { t->t.title.contains(searchText) },
                isSearching = true)

    }


    private fun onComplete(lstCounters: List<Counters>) {

        _countersViewState.value =
            _countersViewState.value?.copy(isLoading = false, error = null,
                countersResult = lstCounters, isSearching = false, isIncrementOrDecrement = false)
    }


    fun onError(message: Int) {
        _countersViewState.value =
            _countersViewState.value?.copy(isLoading = false, error = Error(message), isSearching = false)
    }


    fun newCounter(counters: Counters) {
        createNewCounterJob?.cancel()
        createNewCounterJob = launchCoroutine {
            delay(500)
            createCounter(counters).collect { results ->
                onComplete(results)
            }
        }

    }

    fun loadCounters() {
        EspressoIdlingResource.increment()
        getAllCounterJob?.cancel()
        getAllCounterJob = launchCoroutine {
            delay(500)
            onLoading()
            getAllCounters(Unit).collect { results ->

                onComplete(results)
            }
        }

    }

    fun incrementCounter(counters: Counters) {
        _countersViewState.value =
            _countersViewState.value?.copy(isIncrementOrDecrement = true)
        incrementCounterJob?.cancel()
        incrementCounterJob = launchCoroutine {
            delay(500)
            onLoading()
            incrementCounters(counters).collect { results ->

                onComplete(results)
            }
        }

    }

    fun decrementCounter(counters: Counters) {
        _countersViewState.value =
            _countersViewState.value?.copy(isIncrementOrDecrement = true)
        decrementCounterJob?.cancel()
        decrementCounterJob = launchCoroutine {
            delay(500)
            onLoading()
            decrementCounters(counters).collect { results ->

                onComplete(results)
            }
        }

    }

    fun delete(counters: Counters){
        _countersViewState.value =
            _countersViewState.value?.copy(isDelete = true)
        deleteCounterJob?.cancel()
        deleteCounterJob = launchCoroutine {
            delay(500)
            deleteCounters(counters).collect { results ->
                onComplete(results)
            }

        }
    }



}
