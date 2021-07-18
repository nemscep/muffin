package com.nemscep.muffin.setup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.usecases.SetupProfile
import com.nemscep.muffin.setup.SetupEvents.NavigateToDashboard
import com.nemscep.muffin.setup.SetupEvents.SetupFailed
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import org.junit.Rule
import org.junit.Test

class SetupViewModelTest {
    private val setupProfile: SetupProfile = mockk(relaxed = true)
    private val eventsObserver: Observer<SetupEvents> = spyk()
    private lateinit var tested: SetupViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when setup profile is called, use case is invoked properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { setupProfile(PROFILE, PIN, MAIN_BALANCE) } returns Success(Unit)
            `given tested use case`()

            // When
            tested.setupProfile("Test", 1000, EUR, CURRENT_BALANCE, 1234)

            // Then
            coVerify(exactly = 1) { setupProfile(PROFILE, PIN, MAIN_BALANCE) }
        }

    @Test
    fun `when setup profile is finished successfully, navigation event is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { setupProfile(PROFILE, PIN, MAIN_BALANCE) } returns Success(Unit)
            `given tested use case`()

            // When
            tested.events.observeForever(eventsObserver)
            tested.setupProfile("Test", 1000, EUR, CURRENT_BALANCE, 1234)

            // Then
            verifySequence {
                eventsObserver.onChanged(NavigateToDashboard)
            }
            coVerify(exactly = 1) { setupProfile(PROFILE, PIN, MAIN_BALANCE) }
        }

    @Test
    fun `when setup profile is finished unsuccessfully, error event is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { setupProfile(PROFILE, PIN, MAIN_BALANCE) } returns Failure(Unspecified())
            `given tested use case`()

            // When
            tested.events.observeForever(eventsObserver)
            tested.setupProfile("Test", 1000, EUR, CURRENT_BALANCE, 1234)

            // Then
            verifySequence {
                eventsObserver.onChanged(SetupFailed)
            }
            coVerify(exactly = 1) { setupProfile(PROFILE, PIN, MAIN_BALANCE) }
        }

    private fun `given tested use case`() {
        tested = SetupViewModel(setupProfile = setupProfile)
    }
}

private val PROFILE = Profile(
    name = "Test",
    monthlyIncome = 1000,
    currency = EUR,
)
private const val PIN = 1234
private const val CURRENT_BALANCE = 1234f
private val MAIN_BALANCE = MainBalance(value = CURRENT_BALANCE, currency = PROFILE.currency)
