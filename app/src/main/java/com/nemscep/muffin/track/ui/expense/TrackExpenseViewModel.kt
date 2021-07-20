package com.nemscep.muffin.track.ui.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.track.ui.expense.TrackExpenseEvents.ExpenseTrackingFailed
import com.nemscep.muffin.track.ui.expense.TrackExpenseEvents.ExpenseTrackingSuccessful
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.usecases.AddTransaction
import com.nemscep.muffin.transactions.domain.usecases.GetExpenseCategories
import java.util.Date
import kotlinx.coroutines.launch

class TrackExpenseViewModel(
    private val addTransaction: AddTransaction,
    getBalances: GetBalances,
    getExpenseCategories: GetExpenseCategories
) : ViewModel() {

    private val _events = MutableLiveData<TrackExpenseEvents>()
    val events: LiveData<TrackExpenseEvents> = _events

    val balances: LiveData<List<Balance>> = getBalances().asLiveData()
    val expenseCategories: LiveData<List<ExpenseCategory>> = getExpenseCategories().asLiveData()

    fun trackExpense(
        amount: Float,
        date: Date,
        description: String,
        expenseCategory: ExpenseCategory,
        balance: Balance
    ) {
        viewModelScope.launch {
            val transaction = Expense(
                description = description,
                amount = amount,
                date = date,
                expenseCategory = expenseCategory
            )
            when (addTransaction(transaction, balance)) {
                is Success -> _events.value = ExpenseTrackingSuccessful
                is Failure -> _events.value = ExpenseTrackingFailed
            }
        }
    }
}

/**
 * Sealed class representing all side effects inside topup screen.
 */
sealed class TrackExpenseEvents {
    object ExpenseTrackingSuccessful : TrackExpenseEvents()
    object ExpenseTrackingFailed : TrackExpenseEvents()
}

