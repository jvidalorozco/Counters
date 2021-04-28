package com.cornershop.counterstest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cornershop.counterstest.BaseViewModelTest
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.presentation.viewmodels.BaseViewModel
import com.cornershop.counterstest.presentation.viewmodels.MainScreenViewModel
import com.cornershop.counterstest.testusecases.*
import com.cornershop.counterstest.utils.Data
import com.cornershop.counterstest.utils.UiState
import com.cornershop.counterstest.utils.observeOnce
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
internal class MainScreenViewModelTest: BaseViewModelTest() {
    // region Members

    private lateinit var mainScreenViewModel: MainScreenViewModel

    // endregion


    // region Tests

    @Test
    fun `load data initial when have results`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)

            mainScreenViewModel.loadCounters()

            advanceTimeBy(600)

            mainScreenViewModel.countersViewState.observeOnce { state ->
                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.countersResult).isNotNull()
                Truth.assertThat(state.countersResult).isNotEmpty()

            }
        }
    }

    @Test
    fun `create new counter success`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)

            mainScreenViewModel.newCounter(Counters("isus","new counter",0))

            advanceTimeBy(600)

            mainScreenViewModel.countersViewState.observeOnce { state ->
                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.countersResult).isNotNull()
                Truth.assertThat(state.countersResult).isNotEmpty()

            }
        }
    }


    @Test
    fun `increment counter success`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)

            mainScreenViewModel.incrementCounter(Counters("isus","new counter",0))

            advanceTimeBy(600)

            mainScreenViewModel.countersViewState.observeOnce { state ->
                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.countersResult).isNotNull()
                Truth.assertThat(state.countersResult).isNotEmpty()

            }
        }
    }


    @Test
    fun `derement counter success`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)

            mainScreenViewModel.decrementCounter(Counters("isus","new counter",0))

            advanceTimeBy(600)

            mainScreenViewModel.countersViewState.observeOnce { state ->
                Truth.assertThat(state.error).isNull()
                Truth.assertThat(state.countersResult).isNotNull()
                Truth.assertThat(state.countersResult).isNotEmpty()

            }
        }
    }


    // region BaseViewModelTest

    override fun prepareViewModel(uiState: UiState) {
        val getAllCounters = TestGetAllUseCase(uiState)
        val createNew = TestCreateCounterUseCase(uiState)
        val decrement = TestDecrementUseCase(uiState)
        val increment = TestIncrementUseCase(uiState)
        val delete = TestDeleteUseCase(uiState)

        mainScreenViewModel =
            MainScreenViewModel(
                getAllCounters,
                createNew,
                decrement,
                increment,
                delete
          )
    }

    // endregion

    // region Helpers

    @After
    fun clear() {
       // Data.counters.clear()
    }

    // endregion
}