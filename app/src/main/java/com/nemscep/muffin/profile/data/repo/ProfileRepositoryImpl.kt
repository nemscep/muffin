package com.nemscep.muffin.profile.data.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.models.toDomain
import com.nemscep.muffin.profile.data.models.toEntity
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of [ProfileRepository].
 */
class ProfileRepositoryImpl(
    private val profileDao: ProfileDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepository {

    override val isLoggedIn: Flow<Boolean> =
        profileDao.getProfile()
            .map { it != null }

    override fun getProfile(): Flow<Profile> =
        profileDao.getProfile()
            .filterNotNull()
            .map { it.toDomain() }

    override suspend fun setProfile(profile: Profile): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            profileDao.insertProfile(profileEntity = profile.toEntity())
            return@withContext Success(Unit)
        }
}
