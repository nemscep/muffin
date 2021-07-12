package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository

/**
 * Use case responsible for adding new balance set by user.
 */
class AddBalance(private val balancesRepository: BalancesRepository) {
    suspend operator fun invoke(balance: Balance): Outcome<Unit, CompositeFailure<Nothing>> =
        balancesRepository.addBalance(balance = balance)
}
