package com.nemscep.muffin.auth.domain.usecases

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.CompositeFailure.Specific
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.usecases.PinVerificationError.WrongPin
import kotlinx.coroutines.flow.first

/**
 * Use case responsible for verifying provided pin with the one stored by the user.
 */
class VerifyPin(
    private val getPin: GetPin
) {
    suspend operator fun invoke(pin: Pin): Outcome<Unit, CompositeFailure<PinVerificationError>> =
        if (pin == getPin().first()) Success(Unit) else Failure(Specific(WrongPin))
}

/**
 * Sealed class representing all pin verification errors which might occur.
 */
sealed class PinVerificationError {
    object WrongPin : PinVerificationError()
}
