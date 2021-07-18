package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.balances.domain.repo.BalancesRepository

/**
 * Use case responsible for updating [Balance]'s `isVisibleInOverview` flag.
 */
class UpdateIsVisibleInOverviewFlag(
    private val balancesRepository: BalancesRepository
) {
    suspend operator fun invoke(
        value: Boolean,
        balanceId: Int
    ): Outcome<Unit, CompositeFailure<Nothing>> =
        balancesRepository.changeIsVisibleInOverviewFlag(value = value, balanceId = balanceId)
}
