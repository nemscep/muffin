package com.nemscep.muffin.auth.domain.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.muffin.auth.domain.entities.Pin
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for handling authentication parameters of the currently logged in user.
 */
interface AuthRepository {
    /**
     * Retrieves pin set by user.
     */
    fun getPin(): Flow<Pin>

    /**
     * Stores pin set by user.
     */
    suspend fun setPin(pin: Pin): Outcome<Unit, CompositeFailure<Nothing>>
}
