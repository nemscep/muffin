package com.nemscep.muffin.profile.domain.repo

import com.nemscep.muffin.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for manipulating profile related data.
 */
interface ProfileRepository {
    /**
     * Retrieves current profile [Flow] object.
     */
    fun getProfile(): Flow<Profile>

    /**
     * Stores provided [Profile].
     */
    fun setProfile(profile: Profile)
}
