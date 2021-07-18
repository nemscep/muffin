package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for retrieving transactions list.
 */
class GetTransactions(private val transactionsRepository: TransactionsRepository) {
    operator fun invoke(): Flow<List<Transaction>> = transactionsRepository.transactions
}
