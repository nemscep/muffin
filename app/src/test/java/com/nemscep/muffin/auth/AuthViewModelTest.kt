package com.nemscep.muffin.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.burrito.CompositeFailure.Specific
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.usecases.PinVerificationError.WrongPin
import com.nemscep.muffin.auth.domain.usecases.VerifyPin
import com.nemscep.muffin.auth.ui.AuthEvent
import com.nemscep.muffin.auth.ui.AuthEvent.IncorrectPin
import com.nemscep.muffin.auth.ui.AuthEvent.ToDashboard
import com.nemscep.muffin.auth.ui.AuthViewModel
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {
    private val verifyPin: VerifyPin = mockk(relaxed = true)
    private val eventsObserver: Observer<AuthEvent> = spyk()
    private lateinit var tested: AuthViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when verification is successful, dashboard navigation event is emitted`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { verifyPin(PIN) } returns Success(Unit)
            `given tested view model`()

            // When
            tested.events.observeForever(eventsObserver)
            tested.authenticate(pin = 1234)

            // Then
            verifySequence {
                eventsObserver.onChanged(ToDashboard)
            }
            coVerify(exactly = 1) { verifyPin(PIN) }
        }

    @Test
    fun `when verification is unsuccessful, incorrect pin event is emitted`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { verifyPin(PIN) } returns Failure(Specific(WrongPin))
            `given tested view model`()

            // When
            tested.events.observeForever(eventsObserver)
            tested.authenticate(pin = 1234)

            // Then
            verifySequence {
                eventsObserver.onChanged(IncorrectPin)
            }
            coVerify(exactly = 1) { verifyPin(PIN) }
        }

    private fun `given tested view model`() {
        tested = AuthViewModel(verifyPin = verifyPin)
    }
}

private val PIN = Pin(value = 1234)
