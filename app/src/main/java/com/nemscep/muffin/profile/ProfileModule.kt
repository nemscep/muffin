package com.nemscep.muffin.profile

import com.nemscep.muffin.profile.data.repo.ProfileRepositoryImpl
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import com.nemscep.muffin.profile.domain.usecases.IsLoggedIn
import com.nemscep.muffin.profile.domain.usecases.SetupProfile
import org.koin.dsl.module

val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl() }
    factory { IsLoggedIn(profileRepository = get()) }
    factory { SetupProfile(profileRepository = get(), sessionRepository = get()) }
}
