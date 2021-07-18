package com.nemscep.muffin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.models.BalanceEntity
import com.nemscep.muffin.common.converters.DateConverter
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.models.CurrencyConverter
import com.nemscep.muffin.profile.data.models.ProfileEntity
import com.nemscep.muffin.transactions.data.datasources.ExpenseCategoryConverter
import com.nemscep.muffin.transactions.data.datasources.TransactionDao
import com.nemscep.muffin.transactions.data.datasources.TransactionEntity
import com.nemscep.muffin.transactions.data.datasources.TransactionTypeConverter

@Database(
    entities = [ProfileEntity::class, BalanceEntity::class, TransactionEntity::class],
    version = 1
)
@TypeConverters(
    CurrencyConverter::class,
    TransactionTypeConverter::class,
    ExpenseCategoryConverter::class,
    DateConverter::class
)
abstract class MuffinDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun balanceDao(): BalanceDao
    abstract fun transactionDao(): TransactionDao
}
