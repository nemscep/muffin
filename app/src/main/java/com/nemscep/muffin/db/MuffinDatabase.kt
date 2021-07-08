package com.nemscep.muffin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.models.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1)
abstract class MuffinDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
