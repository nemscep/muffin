package com.nemscep.muffin.profile.data.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Success
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

    override suspend fun setProfile(profile: Profile): Outcome<Unit, CompositeFailure<Nothing>> {
        // TODO("Not yet implemented")
        return Success(Unit)
    }
}
