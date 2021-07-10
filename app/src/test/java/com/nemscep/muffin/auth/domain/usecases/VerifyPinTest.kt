package com.nemscep.muffin.auth.domain.usecases

import com.nemscep.burrito.CompositeFailure.Specific
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.usecases.PinVerificationError.WrongPin
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class VerifyPinTest {
    private val getPin: GetPin = mockk(relaxed = true)
    private lateinit var tested: VerifyPin

    @Test
    fun `when pin is the correct one, success is returned`() = runBlockingTest {
        // Given
        every { getPin() } returns flowOf(PIN)
        tested = VerifyPin(getPin = getPin)

        // When
        val outcome = tested(PIN)

        // Then
        outcome shouldEqual Success(Unit)
    }

    @Test
    fun `when pin is the incorrect one, failure is returned`() = runBlockingTest {
        // Given
        every { getPin() } returns flowOf(PIN)
        tested = VerifyPin(getPin = getPin)

        // When
        val outcome = tested(PIN2)

        // Then
        outcome shouldEqual Failure(Specific(WrongPin))
    }
}

private val PIN = Pin(value = 1234)
private val PIN2 = Pin(value = 4321)
