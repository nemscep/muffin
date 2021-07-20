package com.nemscep.muffin.balances.data.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.models.toDomain
import com.nemscep.muffin.balances.data.models.toEntity
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext

class BalancesRepositoryImpl(
    private val balanceDao: BalanceDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BalancesRepository {
    override fun getBalances(): Flow<List<Balance>> = balanceDao.getBalances()
        .map { balances -> balances.map { it.toDomain() } }

    override fun getMainBalance(): Flow<MainBalance> = getBalances()
        .transform { balances ->
            val mainBalance = balances.filterIsInstance<MainBalance>().firstOrNull()
            if (mainBalance != null) emit(mainBalance)
        }

    override fun getSavingsBalance(): Flow<SavingsBalance> = getBalances()
        .transform { balances ->
            val savingsBalance = balances.filterIsInstance<SavingsBalance>().firstOrNull()
            if (savingsBalance != null) emit(savingsBalance)
        }

    override suspend fun addBalance(balance: Balance): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            try {
                balanceDao.insertBalance(balanceEntity = balance.toEntity())
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }

    override suspend fun deleteBalance(balanceId: Int): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            try {
                balanceDao.deleteBalance(balanceId = balanceId)
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }

    override suspend fun changeIsVisibleInOverviewFlag(
        value: Boolean,
        balanceId: Int
    ): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            try {
                balanceDao.updateIsVisibleInOverviewFlag(value = value, balanceId = balanceId)
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }

    override suspend fun updateBalance(balance: Balance): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            try {
                balanceDao.updateBalance(balanceEntity = balance.toEntity())
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }
}
