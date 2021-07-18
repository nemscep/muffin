package com.nemscep.muffin.transactions.domain.entities

import java.util.Date

/**
 * Domain class representing transactions which user has tracked.
 * There are two types of transactions:
 * - Topups - where user adds value to some balance
 * - Expense - where user subtracts value from some balance
 */
sealed class Transaction {
    abstract val description: String
    abstract val amount: Float
    abstract val date: Date

    data class Expense(
        override val description: String,
        override val amount: Float,
        override val date: Date,
        val expenseCategory: ExpenseCategory
    ) : Transaction()

    data class Topup(
        override val description: String,
        override val amount: Float,
        override val date: Date
    ) : Transaction()
}

// TODO add more expense categories
enum class ExpenseCategory {
    HOME,
    CAR,
    SHOPPING,
    OTHER
}
