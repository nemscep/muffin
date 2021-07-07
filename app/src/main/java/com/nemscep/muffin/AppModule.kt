package com.nemscep.muffin

import com.nemscep.muffin.profile.profileModule
import com.nemscep.muffin.session.sessionModule
import com.nemscep.muffin.splash.splashModule
import org.koin.core.module.Module

/**
 * Koin app module dependencies.
 */
val appModules = listOf<Module>(
    sessionModule,
    splashModule,
    profileModule
)
