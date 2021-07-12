package com.nemscep.muffin.profile.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.async.asyncAll
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.repo.AuthRepository
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.usecases.AddBalance
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import com.nemscep.muffin.session.domain.repo.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Use case responsible for setting up profile.
 */
class SetupProfile(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository,
    private val authRepository: AuthRepository,
    private val addBalance: AddBalance,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        profile: Profile,
        pin: Int,
        currentBalance: Float
    ): Outcome<Unit, CompositeFailure<Any>> =
        asyncAll(
            dispatcher = ioDispatcher,
            { addBalance(MainBalance(currency = profile.currency, value = currentBalance)) },
            { authRepository.setPin(Pin(value = pin)) },
            { profileRepository.setProfile(profile) },
            { sessionRepository.openSession() })
}
