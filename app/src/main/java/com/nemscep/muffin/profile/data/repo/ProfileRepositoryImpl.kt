package com.nemscep.muffin.profile.data.repo

import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Concrete implementation of [ProfileRepository].
 */
class ProfileRepositoryImpl() : ProfileRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = flowOf(true)

    override fun getProfile(): Flow<Profile> {
        TODO("Not yet implemented")
    }

    override fun setProfile(profile: Profile) {
        TODO("Not yet implemented")
    }
}