package com.nemscep.muffin.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "muffin-data-store")
val dataStoreModule = module {
    single<DataStore<Preferences>> {
        androidApplication().dataStore
    }
}
