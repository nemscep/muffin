package com.nemscep.muffin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.models.BalanceEntity
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.models.CurrencyConverter
import com.nemscep.muffin.profile.data.models.ProfileEntity

@Database(entities = [ProfileEntity::class, BalanceEntity::class], version = 1)
@TypeConverters(CurrencyConverter::class)
abstract class MuffinDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun balanceDao(): BalanceDao
}
