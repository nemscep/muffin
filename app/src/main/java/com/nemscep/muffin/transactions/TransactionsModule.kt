package com.nemscep.muffin.transactions

import com.nemscep.muffin.db.MuffinDatabase
import com.nemscep.muffin.transactions.data.datasources.TransactionDao
import org.koin.dsl.module

val transactionsModule = module {
    single<TransactionDao> { get<MuffinDatabase>().transactionDao() }
}
