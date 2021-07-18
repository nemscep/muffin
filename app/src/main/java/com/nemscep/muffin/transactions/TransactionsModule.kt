package com.nemscep.muffin.transactions

import com.nemscep.muffin.db.MuffinDatabase
import com.nemscep.muffin.transactions.data.datasources.TransactionDao
import com.nemscep.muffin.transactions.data.repo.TransactionsRepositoryImpl
import com.nemscep.muffin.transactions.domain.repo.TransactionsRepository
import com.nemscep.muffin.transactions.domain.usecases.GetTransactions
import org.koin.dsl.module

val transactionsModule = module {
    single<TransactionDao> { get<MuffinDatabase>().transactionDao() }
    single<TransactionsRepository> { TransactionsRepositoryImpl(transactionDao = get()) }
    factory { GetTransactions(transactionsRepository = get()) }
}
