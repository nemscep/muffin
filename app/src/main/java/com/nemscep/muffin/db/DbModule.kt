package com.nemscep.muffin.db

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single<MuffinDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            MuffinDatabase::class.java,
            "Muffin database"
        ).build()
    }
}
