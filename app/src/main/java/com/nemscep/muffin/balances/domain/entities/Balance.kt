package com.nemscep.muffin.balances.domain.entities

import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.profile.domain.entities.Currency

/**
 * Domain class representing balance object representation.
 * There are multiple type of balances, each representing a separate class.
 * These are:
 * [MainBalance] - represents balance as in full-amount-of-money-user-has.
 * [SavingsBalance] - represents savings balance as in full-amount-of-money-saved-by-user.
 * [SpecificBalance] - represents balance specific to a certain-goal-user-intends-money-for.
 */
sealed class Balance {
    abstract val id: Int
    abstract val value: Float
    abstract val currency: Currency

    data class MainBalance(
        override val id: Int,
        override val value: Float,
        override val currency: Currency
    ) : Balance()

    data class SavingsBalance(
        override val id: Int,
        override val value: Float,
        override val currency: Currency
    ) : Balance()

    /**
     * Specific balance defined by @param name.
     */
    data class SpecificBalance(
        override val id: Int,
        val name: String,
        override val value: Float,
        override val currency: Currency
    ) : Balance()
}
