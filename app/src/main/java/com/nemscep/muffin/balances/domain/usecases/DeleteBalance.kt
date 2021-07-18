package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.balances.domain.repo.BalancesRepository

/**
 * Use case responsible for deletion balance set by user.
 */
class DeleteBalance(private val balancesRepository: BalancesRepository) {
    suspend operator fun invoke(balanceId: Int): Outcome<Unit, CompositeFailure<Nothing>> =
        balancesRepository.deleteBalance(balanceId = balanceId)
}
