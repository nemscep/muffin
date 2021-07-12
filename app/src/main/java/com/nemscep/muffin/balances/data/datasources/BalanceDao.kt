package com.nemscep.muffin.balances.data.datasources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nemscep.muffin.balances.data.models.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Query("SELECT * FROM ${BalanceEntity.TABLE_NAME}")
    fun getBalances(): Flow<List<BalanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balanceEntity: BalanceEntity)

    @Delete
    suspend fun deleteBalance(balanceEntity: BalanceEntity)

}
