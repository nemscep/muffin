package com.nemscep.muffin.profile.domain.usecases

import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for retrieving logged in state.
 */
class IsLoggedIn(private val profileRepository: ProfileRepository) {
    operator fun invoke(): Flow<Boolean> = profileRepository.isLoggedIn
}
