package com.nemscep.muffin.balances.domain.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for handling balance manipulations of the currently logged in user.
 */
interface BalancesRepository {

    /**
     * Retrieves all balances set by the user.
     */
    fun getBalances(): Flow<List<Balance>>

    /**
     * Retrieves main balance object.
     */
    fun getMainBalance(): Flow<MainBalance>

    /**
     * Retrieves savings balance.
     */
    fun getSavingsBalance(): Flow<SavingsBalance>

    /**
     * Stores balance set by user.
     */
    suspend fun addBalance(balance: Balance): Outcome<Unit, CompositeFailure<Nothing>>

    /**
     * Deletes provided balance.
     */
    suspend fun deleteBalance(balanceId: Int): Outcome<Unit, CompositeFailure<Nothing>>

    /**
     * Changes isVisibleInOverview flag to provided value
     */
    suspend fun changeIsVisibleInOverviewFlag(
        value: Boolean,
        balanceId: Int
    ): Outcome<Unit, CompositeFailure<Nothing>>

    /**
     * Updates balance provided as an argument.
     */
    suspend fun updateBalance(balance: Balance): Outcome<Unit, CompositeFailure<Nothing>>
}
