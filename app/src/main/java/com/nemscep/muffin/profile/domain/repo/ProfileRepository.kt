package com.nemscep.muffin.profile.domain.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
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
    suspend fun setProfile(profile: Profile): Outcome<Unit, CompositeFailure<Nothing>>

    /**
     * Retrieves [Boolean][Flow] of whether user is logged in or not.
     */
    val isLoggedIn: Flow<Boolean>
}
