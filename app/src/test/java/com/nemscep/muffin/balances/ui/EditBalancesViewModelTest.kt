package com.nemscep.muffin.balances.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.usecases.DeleteBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.balances.domain.usecases.UpdateIsVisibleInOverviewFlag
import com.nemscep.muffin.balances.ui.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class EditBalancesViewModelTest {
    private val getBalances: GetBalances = mockk(relaxed = true)
    private val deleteBalance: DeleteBalance = mockk(relaxed = true)
    private val updateIsVisibleInOverviewFlag: UpdateIsVisibleInOverviewFlag = mockk(relaxed = true)
    private val balancesObserver: Observer<List<BalanceUiModel>> = spyk()
    private lateinit var tested: EditBalancesViewModel

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when balances exist, they are emitted properly`() = testCoroutineRule.runBlockingTest {
        // Given
        every { getBalances() } returns flowOf(BALANCES)
        `given tested view model`()

        // When
        tested.balances.observeForever(balancesObserver)

        // Then
        verifySequence {
            balancesObserver.onChanged(BALANCES_UI_MODELS)
        }
    }

    @Test
    fun `when delete balance call is invoked, use case is called`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { deleteBalance(SAVINGS_BALANCE.id) } returns Success(Unit)
            `given tested view model`()

            // When
            tested.delete(SAVINGS_BALANCE_UI_MODEL.id)

            // Then
            coVerify(exactly = 1) {
                deleteBalance(SAVINGS_BALANCE_UI_MODEL.id)
            }
        }

    @Test
    fun `when visibilityChanged call is invoked, use case is called`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery {
                updateIsVisibleInOverviewFlag(
                    value = true,
                    balanceId = SAVINGS_BALANCE.id
                )
            } returns Success(Unit)
            `given tested view model`()

            // When
            tested.visibilityChanged(isChecked = true, balanceId = SAVINGS_BALANCE_UI_MODEL.id)

            // Then
            coVerify(exactly = 1) {
                updateIsVisibleInOverviewFlag(
                    value = true,
                    balanceId = SAVINGS_BALANCE.id
                )
            }
        }

    private fun `given tested view model`() {
        tested = EditBalancesViewModel(
            getBalances = getBalances,
            deleteBalance = deleteBalance,
            updateIsVisibleInOverviewFlag = updateIsVisibleInOverviewFlag
        )
    }
}

private val MAIN_BALANCE =
    MainBalance(value = 2f, currency = EUR, id = 1, isVisibleInOverview = true)
private val SAVINGS_BALANCE =
    SavingsBalance(value = 3f, currency = EUR, id = 2, isVisibleInOverview = true)
private val BALANCES = listOf(MAIN_BALANCE, SAVINGS_BALANCE)

private val MAIN_BALANCE_UI_MODEL =
    MainBalanceUiModel(value = 2f, currency = "EUR", id = 1, isVisibleInOverview = true)
private val SAVINGS_BALANCE_UI_MODEL =
    SavingsBalanceUiModel(value = 3f, currency = "EUR", id = 2, isVisibleInOverview = true)
private val BALANCES_UI_MODELS = listOf(MAIN_BALANCE_UI_MODEL, SAVINGS_BALANCE_UI_MODEL)
