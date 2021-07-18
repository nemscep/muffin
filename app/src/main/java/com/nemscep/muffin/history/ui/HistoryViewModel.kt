package com.nemscep.muffin.history.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nemscep.muffin.history.ui.TransactionUiModel.ExpenseUiModel
import com.nemscep.muffin.history.ui.TransactionUiModel.TopupUiModel
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory
import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.usecases.GetTransactions
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class HistoryViewModel(
    getTransactions: GetTransactions,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    val transactions: LiveData<List<TransactionUiModel>> = getTransactions()
        .map { transactions -> transactions.map { it.toUiModel() } }
        .asLiveData(defaultDispatcher)
}

private fun Transaction.toUiModel(): TransactionUiModel = when (this) {
    is Expense -> ExpenseUiModel(
        amount = amount.toString(),
        date = dateFormat.format(date),
        expenseCategory = expenseCategory,
        description = description
    )
    is Topup -> TopupUiModel(
        amount = amount.toString(),
        date = dateFormat.format(date),
        description = description
    )
}

private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

/**
 * Sealed class representing [Transaction] ui model.
 */
sealed class TransactionUiModel {
    abstract val description: String
    abstract val amount: String
    abstract val date: String

    data class ExpenseUiModel(
        override val description: String,
        override val amount: String,
        override val date: String,
        val expenseCategory: ExpenseCategory
    ) : TransactionUiModel()

    data class TopupUiModel(
        override val description: String,
        override val amount: String,
        override val date: String
    ) : TransactionUiModel()
}
