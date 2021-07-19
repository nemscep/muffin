package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.util.Date
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class AddTransactionTest {
    private val transactionsRepository: TransactionsRepository = mockk(relaxed = true)
    private lateinit var tested: AddTransaction

    @Test
    fun `when repository call is successful, success is returned`() = runBlockingTest {
        // Given
        coEvery { transactionsRepository.addTransaction(EXPENSE) } returns Success(Unit)
        `given tested use case`()

        // When
        val outcome = tested(EXPENSE)

        // Then
        outcome shouldEqual Success(Unit)
    }

    @Test
    fun `when repository call is unsuccessful, failure is returned`() = runBlockingTest {
        // Given
        coEvery { transactionsRepository.addTransaction(EXPENSE) } returns Failure(Unspecified())
        `given tested use case`()

        // When
        val outcome = tested(EXPENSE)

        // Then
        outcome shouldEqual Failure(Unspecified())
    }

    private fun `given tested use case`() {
        tested = AddTransaction(transactionsRepository = transactionsRepository)
    }
}

private val EXPENSE = Expense(
    amount = 2f,
    date = Date(1),
    expenseCategory = OTHER,
    description = ""
)
private val TOPUP =
    Topup(amount = 2f, date = Date(1), description = "")
