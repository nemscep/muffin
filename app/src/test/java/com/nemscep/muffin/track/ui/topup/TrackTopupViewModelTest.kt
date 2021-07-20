package com.nemscep.muffin.track.ui.topup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingFailed
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingSuccessful
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.usecases.AddTransaction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import java.util.Date
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TrackTopupViewModelTest {
    private val addTransaction: AddTransaction = mockk(relaxed = true)
    private val getBalances: GetBalances = mockk(relaxed = true)
    private val balancesObserver: Observer<List<Balance>> = spyk()
    private val eventsObserver: Observer<TrackTopupEvents> = spyk()
    private lateinit var tested: TrackTopupViewModel

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
            balancesObserver.onChanged(BALANCES)
        }
    }

    @Test
    fun `when add transaction call is invoked, use case is called`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val transaction = Topup(description = "", amount = 2f, date = Date(1))
            coEvery {
                addTransaction(
                    transaction = transaction,
                    balance = MAIN_BALANCE
                )
            } returns Success(Unit)
            `given tested view model`()

            // When
            tested.trackTopup(
                amount = 2f,
                description = "",
                date = Date(1),
                balance = MAIN_BALANCE
            )

            // Then
            coVerify(exactly = 1) {
                addTransaction(transaction = transaction, balance = MAIN_BALANCE)
            }
        }

    @Test
    fun `when add transaction call is successful, TrackingSuccessful side effect is emitted `() =
        testCoroutineRule.runBlockingTest {
            // Given
            val transaction = Topup(description = "", amount = 2f, date = Date(1))
            coEvery {
                addTransaction(
                    transaction = transaction,
                    balance = MAIN_BALANCE
                )
            } returns Success(Unit)
            `given tested view model`()

            // When
            tested.trackTopup(
                amount = 2f,
                description = "",
                date = Date(1),
                balance = MAIN_BALANCE
            )
            tested.events.observeForever(eventsObserver)

            // Then
            verifySequence {
                eventsObserver.onChanged(TrackingSuccessful)
            }
            coVerify(exactly = 1) {
                addTransaction(transaction = transaction, balance = MAIN_BALANCE)
            }
        }

    @Test
    fun `when add transaction call is unsuccessful, TrackingFailed side effect is emitted `() =
        testCoroutineRule.runBlockingTest {
            // Given
            val transaction = Topup(description = "", amount = 2f, date = Date(1))
            coEvery {
                addTransaction(
                    transaction = transaction,
                    balance = MAIN_BALANCE
                )
            } returns Failure(Unspecified())
            `given tested view model`()

            // When
            tested.trackTopup(
                amount = 2f,
                description = "",
                date = Date(1),
                balance = MAIN_BALANCE
            )
            tested.events.observeForever(eventsObserver)

            // Then
            verifySequence {
                eventsObserver.onChanged(TrackingFailed)
            }
            coVerify(exactly = 1) {
                addTransaction(transaction = transaction, balance = MAIN_BALANCE)
            }
        }

    private fun `given tested view model`() {
        tested = TrackTopupViewModel(addTransaction = addTransaction, getBalances = getBalances)
    }
}

private val MAIN_BALANCE =
    MainBalance(value = 2f, currency = EUR, id = 1, isVisibleInOverview = true)
private val SAVINGS_BALANCE =
    SavingsBalance(value = 3f, currency = EUR, id = 2, isVisibleInOverview = true)
private val BALANCES = listOf(MAIN_BALANCE, SAVINGS_BALANCE)
