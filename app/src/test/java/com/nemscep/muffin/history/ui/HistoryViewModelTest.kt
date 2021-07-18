package com.nemscep.muffin.history.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.history.ui.TransactionUiModel.ExpenseUiModel
import com.nemscep.muffin.history.ui.TransactionUiModel.TopupUiModel
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.usecases.GetTransactions
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import java.util.Date
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {
    private val getTransactions: GetTransactions = mockk(relaxed = true)
    private val transactionsObserver: Observer<List<TransactionUiModel>> = spyk()
    private lateinit var tested: HistoryViewModel

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when transactions are emitted, they are mapped to Ui models properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { getTransactions() } returns flowOf(TRANSACTIONS)
            `given tested view model`()

            // When
            tested.transactions.observeForever(transactionsObserver)

            // Then
            verifySequence {
                transactionsObserver.onChanged(TRANSACTIONS_UI_MODEL)
            }
        }

    private fun `given tested view model`() {
        tested = HistoryViewModel(
            getTransactions = getTransactions,
            defaultDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val DATE = Date(1626638955000)
private val EXPENSE = Expense(amount = 2f, date = DATE, expenseCategory = OTHER, description = "")
private val TOPUP = Topup(amount = 2f, date = DATE, description = "")

private val TRANSACTIONS = listOf(EXPENSE, TOPUP)

private val EXPENSE_UI_MODEL =
    ExpenseUiModel(amount = "2.0", date = "18/07/2021", expenseCategory = OTHER, description = "")
private val TOPUP_UI_MODEl = TopupUiModel(amount = "2.0", date = "18/07/2021", description = "")
private val TRANSACTIONS_UI_MODEL = listOf(EXPENSE_UI_MODEL, TOPUP_UI_MODEl)
