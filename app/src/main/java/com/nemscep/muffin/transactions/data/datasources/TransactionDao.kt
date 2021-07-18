package com.nemscep.muffin.transactions.data.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM ${TransactionEntity.TABLE_NAME}")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insertTransaction(transactionEntity: TransactionEntity)
}
