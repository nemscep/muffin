package com.nemscep.muffin.transactions.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class GetTransactionsTest {
    private val transactionsRepository: TransactionsRepository = mockk(relaxed = true)
    private lateinit var tested: GetTransactions

    @ExperimentalTime
    @Test
    fun `when repository has transactions they are returned properly`() = runBlockingTest {
        // Given
        every { transactionsRepository.transactions } returns flowOf(TRANSACTIONS)
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual TRANSACTIONS
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when repository has no transactions, empty list is returned properly`() = runBlockingTest {
        // Given
        every { transactionsRepository.transactions } returns flowOf(emptyList())
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual emptyList()
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun `given tested use case`() {
        tested = GetTransactions(transactionsRepository = transactionsRepository)
    }
}

private val DATE = Date(1)
private val EXPENSE = Expense(amount = 2f, date = DATE, expenseCategory = OTHER)
private val TOPUP = Topup(amount = 2f, date = DATE)

private val TRANSACTIONS = listOf(EXPENSE, TOPUP)
