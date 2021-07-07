package com.nemscep.muffin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

class MuffinApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MuffinApplication)
            modules(appModules)
        }
    }
}
