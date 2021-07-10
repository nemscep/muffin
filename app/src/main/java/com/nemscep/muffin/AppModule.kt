package com.nemscep.muffin

import com.nemscep.muffin.auth.authModule
import com.nemscep.muffin.datastore.dataStoreModule
import com.nemscep.muffin.db.dbModule
import com.nemscep.muffin.overview.overviewModule
import com.nemscep.muffin.profile.profileModule
import com.nemscep.muffin.session.sessionModule
import com.nemscep.muffin.setup.setupModule
import com.nemscep.muffin.splash.splashModule
import org.koin.core.module.Module

/**
 * Koin app module dependencies.
 */
val appModules = listOf<Module>(
    sessionModule,
    splashModule,
    profileModule,
    setupModule,
    dbModule,
    overviewModule,
    authModule,
    dataStoreModule
)
