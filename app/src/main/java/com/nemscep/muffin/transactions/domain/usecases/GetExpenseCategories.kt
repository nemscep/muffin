package com.nemscep.muffin.transactions.domain.usecases

import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.CAR
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.HOME
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.SHOPPING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Use case responsible for returning list of all supported [ExpenseCategory]s.
 */
class GetExpenseCategories {
    operator fun invoke(): Flow<List<ExpenseCategory>> = flowOf(EXPENSE_CATEGORIES)
}


/**
 * Represents list of all [ExpenseCategory] elements.
 */
val EXPENSE_CATEGORIES = listOf(HOME, CAR, SHOPPING, OTHER)
