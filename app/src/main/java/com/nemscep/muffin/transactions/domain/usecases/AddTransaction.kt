package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository

/**
 * Use case responsible for adding transaction set by user.
 */
class AddTransaction(private val transactionsRepository: TransactionsRepository) {
    suspend operator fun invoke(transaction: Transaction): Outcome<Unit, CompositeFailure<Nothing>> =
        transactionsRepository.addTransaction(transaction = transaction)
}
