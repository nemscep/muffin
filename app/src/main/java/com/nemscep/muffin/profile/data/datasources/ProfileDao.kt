package com.nemscep.muffin.profile.data.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nemscep.muffin.profile.data.models.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM ${ProfileEntity.TABLE_NAME}")
    fun getProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM ${ProfileEntity.TABLE_NAME}")
    suspend fun deleteProfile()
}
