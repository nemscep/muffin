package com.nemscep.muffin.transactions.data.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.transactions.data.datasources.TransactionDao
import com.nemscep.muffin.transactions.data.datasources.toDomain
import com.nemscep.muffin.transactions.data.datasources.toEntity
import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TransactionsRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TransactionsRepository {
    override val transactions: Flow<List<Transaction>> = transactionDao.getTransactions()
        .map { transactions -> transactions.map { it.toDomain() } }

    override suspend fun addTransaction(transaction: Transaction): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            try {
                transactionDao.insertTransaction(transactionEntity = transaction.toEntity())
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }
}
