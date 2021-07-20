package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.util.Date
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class AddTransactionTest {
    private val transactionsRepository: TransactionsRepository = mockk(relaxed = true)
    private val balanceRepository: BalancesRepository = mockk(relaxed = true)
    private lateinit var tested: AddTransaction

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when repository calls are successful, success is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val newValue = MAIN_BALANCE.value - EXPENSE.amount
            coEvery { transactionsRepository.addTransaction(EXPENSE) } returns Success(Unit)
            coEvery { balanceRepository.updateBalance(MAIN_BALANCE.copy(value = newValue)) } coAnswers {
                Success(Unit)
            }
            `given tested use case`()

            // When
            val outcome =
                tested(transaction = EXPENSE, balance = MAIN_BALANCE)

            // Then
            outcome shouldEqual Success(Unit)
        }

    @Test
    fun `when transactions repository call is unsuccessful, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { transactionsRepository.addTransaction(EXPENSE) } returns Failure(Unspecified())
            `given tested use case`()

            // When
            val outcome = tested(transaction = EXPENSE, balance = MAIN_BALANCE)

            // Then
            outcome shouldEqual Failure(Unspecified())
        }

    @Test
    fun `when balances repository call is unsuccessful, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val newValue = MAIN_BALANCE.value - EXPENSE.amount
            coEvery { transactionsRepository.addTransaction(EXPENSE) } returns Success(Unit)
            coEvery { balanceRepository.updateBalance(MAIN_BALANCE.copy(value = newValue)) } coAnswers {
                Failure(Unspecified())
            }
            `given tested use case`()

            // When
            val outcome = tested(transaction = EXPENSE, balance = MAIN_BALANCE)

            // Then
            outcome shouldEqual Failure(Unspecified())
        }

    private fun `given tested use case`() {
        tested = AddTransaction(
            transactionsRepository = transactionsRepository,
            balancesRepository = balanceRepository,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val EXPENSE = Expense(
    amount = 2f,
    date = Date(1),
    expenseCategory = OTHER,
    description = ""
)
private val TOPUP = Topup(amount = 2f, date = Date(1), description = "")
private val MAIN_BALANCE =
    MainBalance(value = 2f, currency = EUR, id = 1, isVisibleInOverview = true)
