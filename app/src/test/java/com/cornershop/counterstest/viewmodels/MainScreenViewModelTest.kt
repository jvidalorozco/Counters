package com.cornershop.counterstest.viewmodels

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cornershop.counterstest.BaseViewModelTest
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
    fun `given a character url when character details request sent then get character details`() {
        coroutineTestRule.dispatcher.runBlockingTest {
            prepareViewModel(UiState.SUCCESS)

            mainScreenViewModel.loadCounters()

            mainScreenViewModel.countersViewState.observeOnce { detailViewState ->
                Truth.assertThat(detailViewState.error).isNull()
                Truth.assertThat(detailViewState.countersResult).isNotEmpty()

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