package com.nemscep.muffin.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.MainBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SavingsBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SpecificBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalancesHeader
import com.nemscep.muffin.overview.OverviewItem.TransactionsOverviewHeader
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class OverviewViewModelTest {
    private val getBalances: GetBalances = mockk(relaxed = true)
    private val overviewItemsObserver: Observer<List<OverviewItem>> = spyk()
    private lateinit var tested: OverviewViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `overview items list are mapped properly`() = testCoroutineRule.runBlockingTest {
        // Given
        every { getBalances() } returns flowOf(BALANCES)
        `given tested view model`()

        // When
        tested.overviewItems.observeForever(overviewItemsObserver)

        // Then
        verifySequence { overviewItemsObserver.onChanged(OVERVIEW_ITEMS) }
    }

    private fun `given tested view model`() {
        tested = OverviewViewModel(getBalances = getBalances)
    }
}

private val BALANCES = listOf(
    MainBalance(value = 2f, currency = EUR, id = 1),
    SavingsBalance(value = 3f, currency = EUR, id = 2),
    SpecificBalance(value = 2f, name = "Test", currency = EUR, id = 3)
)

private val OVERVIEW_ITEMS = listOf(
    BalancesHeader,
    MainBalanceOverviewUiModel(value = 2f, currency = "EUR"),
    SavingsBalanceOverviewUiModel(value = 3f, currency = "EUR"),
    SpecificBalanceOverviewUiModel(name = "Test", value = 2f, currency = "EUR"),
    TransactionsOverviewHeader
)
