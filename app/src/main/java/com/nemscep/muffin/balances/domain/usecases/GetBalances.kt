package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for returning balances list set by user.
 */
class GetBalances(private val balancesRepository: BalancesRepository) {
    operator fun invoke(): Flow<List<Balance>> = balancesRepository.getBalances()
}
