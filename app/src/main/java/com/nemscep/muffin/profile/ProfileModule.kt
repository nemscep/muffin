package com.nemscep.muffin.profile

import com.nemscep.muffin.db.MuffinDatabase
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.repo.ProfileRepositoryImpl
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import com.nemscep.muffin.profile.domain.usecases.GetProfile
import com.nemscep.muffin.profile.domain.usecases.IsLoggedIn
import com.nemscep.muffin.profile.domain.usecases.SetupProfile
import org.koin.dsl.module

val profileModule = module {
    single<ProfileDao> { get<MuffinDatabase>().profileDao() }
    single<ProfileRepository> { ProfileRepositoryImpl(profileDao = get()) }
    factory { IsLoggedIn(profileRepository = get()) }
    factory {
        SetupProfile(
            profileRepository = get(),
            sessionRepository = get(),
            authRepository = get(),
            addBalance = get()
        )
    }
    factory { GetProfile(profileRepository = get()) }
}
