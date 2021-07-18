package com.nemscep.muffin.transactions.data.repo

import app.cash.turbine.test
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.transactions.data.datasources.TransactionDao
import com.nemscep.muffin.transactions.data.datasources.TransactionEntity
import com.nemscep.muffin.transactions.data.datasources.TransactionType
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class TransactionsRepositoryImplTest {
    private val transactionDao: TransactionDao = mockk(relaxed = true)
    private lateinit var tested: TransactionsRepositoryImpl

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ExperimentalTime
    @Test
    fun `when db contains transactions, they are emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { transactionDao.getTransactions() } returns flowOf(TRANSACTION_ENTITIES)
            `given tested repository`()

            // When

            // Then
            tested.transactions.test {
                expectItem() shouldEqual TRANSACTIONS
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db does not contain transactions, empty list is emitted`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { transactionDao.getTransactions() } returns flowOf(emptyList())
            `given tested repository`()

            // When

            // Then
            tested.transactions.test {
                expectItem() shouldEqual emptyList()
                cancelAndIgnoreRemainingEvents()
            }
        }


    private fun `given tested repository`() {
        tested = TransactionsRepositoryImpl(
            transactionDao = transactionDao,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val DATE = Date(1)
private val EXPENSE = Expense(amount = 2f, date = DATE, expenseCategory = OTHER, description = "")
private val TOPUP = Topup(amount = 2f, date = DATE, description = "")

private val TRANSACTIONS = listOf(EXPENSE, TOPUP)

private val EXPENSE_ENTITY = TransactionEntity(
    type = TransactionType.EXPENSE,
    amount = 2f,
    expenseCategory = OTHER,
    date = DATE,
    description = ""
)
private val TOPUP_ENTITY = TransactionEntity(
    type = TransactionType.TOPUP,
    amount = 2f,
    date = DATE,
    description = ""
)
private val TRANSACTION_ENTITIES = listOf(EXPENSE_ENTITY, TOPUP_ENTITY)
