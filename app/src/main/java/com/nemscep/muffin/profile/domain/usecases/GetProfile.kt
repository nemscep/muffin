package com.nemscep.muffin.profile.domain.usecases

import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for returning profile object.
 */
class GetProfile(private val profileRepository: ProfileRepository) {
    operator fun invoke(): Flow<Profile> = profileRepository.getProfile()
}
