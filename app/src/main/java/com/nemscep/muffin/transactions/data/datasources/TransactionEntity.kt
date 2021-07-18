package com.nemscep.muffin.transactions.data.datasources

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.nemscep.muffin.transactions.data.datasources.TransactionType.EXPENSE
import com.nemscep.muffin.transactions.data.datasources.TransactionType.TOPUP
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.CAR
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.HOME
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.OTHER
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory.SHOPPING
import com.nemscep.muffin.transactions.domain.entities.Transaction
import com.nemscep.muffin.transactions.domain.entities.Transaction.Expense
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import java.util.Date

@Entity(tableName = TransactionEntity.TABLE_NAME)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: TransactionType,
    val amount: Float,
    val expenseCategory: ExpenseCategory? = null,
    val date: Date
) {
    companion object {
        const val TABLE_NAME = "transactions"
    }
}

fun Transaction.toEntity(): TransactionEntity = when (this) {
    is Expense -> TransactionEntity(
        type = EXPENSE,
        amount = amount,
        expenseCategory = expenseCategory,
        date = date
    )
    is Topup -> TransactionEntity(
        type = TOPUP,
        amount = amount,
        date = date,
    )
}

fun TransactionEntity.toDomain(): Transaction = when (type) {
    EXPENSE -> Expense(amount = amount, date = date, expenseCategory = expenseCategory!!)
    TOPUP -> Topup(amount = amount, date)
}

enum class TransactionType { EXPENSE, TOPUP }

class TransactionTypeConverter {
    @TypeConverter
    fun fromJson(transactionType: Int): TransactionType =
        when (transactionType) {
            0 -> TOPUP
            1 -> EXPENSE
            else -> TODO()
        }

    @TypeConverter
    fun toJson(transactionType: TransactionType): Int =
        when (transactionType) {
            TOPUP -> 0
            EXPENSE -> 1
        }
}

class ExpenseCategoryConverter {
    @TypeConverter
    fun fromJson(expenseCategory: Int): ExpenseCategory =
        when (expenseCategory) {
            0 -> HOME
            1 -> CAR
            2 -> SHOPPING
            3 -> OTHER
            else -> TODO()
        }

    @TypeConverter
    fun toJson(expenseCategory: ExpenseCategory): Int =
        when (expenseCategory) {
            HOME -> 0
            CAR -> 1
            SHOPPING -> 2
            OTHER -> 3
        }
}
