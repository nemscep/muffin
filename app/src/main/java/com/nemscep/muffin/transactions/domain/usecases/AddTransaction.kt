package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.async.asyncAll
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Use case responsible for adding transaction set by user.
 */
class AddTransaction(
    private val transactionsRepository: TransactionsRepository,
    private val balancesRepository: BalancesRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        transaction: Transaction,
        balance: Balance
    ): Outcome<Unit, CompositeFailure<Nothing>> =
        asyncAll(
            dispatcher = ioDispatcher,
            { transactionsRepository.addTransaction(transaction = transaction) },
            {
                val isTopup = transaction is Topup
                val newValue =
                    if (isTopup) balance.value + transaction.amount else balance.value - transaction.amount
                val newBalance = when (balance) {
                    is MainBalance -> balance.copy(value = newValue)
                    is SavingsBalance -> balance.copy(value = newValue)
                    is SpecificBalance -> balance.copy(value = newValue)
                }
                balancesRepository.updateBalance(newBalance)
            },
            fallbackFailure = Unspecified()
        )
}
