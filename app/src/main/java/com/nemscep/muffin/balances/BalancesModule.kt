package com.nemscep.muffin.balances

import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.repo.BalancesRepositoryImpl
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.db.MuffinDatabase
import org.koin.dsl.module

val balancesModule = module {
    single<BalanceDao> { get<MuffinDatabase>().balanceDao() }
    single<BalancesRepository> { BalancesRepositoryImpl(balanceDao = get()) }
}
