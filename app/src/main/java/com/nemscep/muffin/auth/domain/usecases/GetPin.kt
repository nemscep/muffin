package com.nemscep.muffin.auth.domain.usecases

import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for retrieving pin set by user from the repository.
 */
class GetPin(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Pin> = authRepository.getPin()
}
