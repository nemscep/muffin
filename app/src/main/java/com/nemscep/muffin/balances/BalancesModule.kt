package com.nemscep.muffin.balances

import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.repo.BalancesRepositoryImpl
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.balances.domain.usecases.AddBalance
import com.nemscep.muffin.balances.domain.usecases.DeleteBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.balances.domain.usecases.UpdateIsVisibleInOverviewFlag
import com.nemscep.muffin.balances.ui.EditBalancesViewModel
import com.nemscep.muffin.db.MuffinDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val balancesModule = module {
    single<BalanceDao> { get<MuffinDatabase>().balanceDao() }
    single<BalancesRepository> { BalancesRepositoryImpl(balanceDao = get()) }
    factory { GetBalances(balancesRepository = get()) }
    factory { AddBalance(balancesRepository = get()) }
    factory { DeleteBalance(balancesRepository = get()) }
    factory { UpdateIsVisibleInOverviewFlag(balancesRepository = get()) }
    viewModel {
        EditBalancesViewModel(
            deleteBalance = get(),
            getBalances = get(),
            updateIsVisibleInOverviewFlag = get()
        )
    }
}
