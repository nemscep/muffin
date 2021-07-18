package com.nemscep.muffin.transactions.domain.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.transactions.domain.entities.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for storing/retrieving expenses or topups which user has tracked.
 */
interface TransactionsRepository {
    /**
     * Returns [Flow] object containing all transactions tracked by user.
     */
    val transactions: Flow<List<Transaction>>

    /**
     * Adds new transaction which user has tracked.
     */
    suspend fun addTransaction(transaction: Transaction): Outcome<Unit, CompositeFailure<Nothing>>
}
