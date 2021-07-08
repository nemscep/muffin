package com.nemscep.muffin.profile.domain.usecases

import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.CompositeFailure.NoInternet
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import com.nemscep.muffin.session.domain.repo.SessionRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class SetupProfileTest {
    private val profileRepository: ProfileRepository = mockk(relaxed = true)
    private val sessionRepository: SessionRepository = mockk(relaxed = true)
    private lateinit var tested: SetupProfile

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when session and profile repositories return success, success is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { profileRepository.setProfile(PROFILE) } returns Success(Unit)
            coEvery { sessionRepository.openSession() } returns Success(Unit)

            `given tested use case`()

            // When
            val outcome = tested(profile = PROFILE, pin = 1234, currentBalance = 1234f)

            // Then
            outcome shouldEqual Success(Unit)
        }

    @Test
    fun `when session repository returns failure, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { profileRepository.setProfile(PROFILE) } returns Success(Unit)
            coEvery { sessionRepository.openSession() } returns Failure(Unspecified())

            `given tested use case`()

            // When
            val outcome = tested(profile = PROFILE, pin = 1234, currentBalance = 1234f)

            // Then
            outcome shouldEqual Failure(Unspecified())
        }

    @Test
    fun `when profile repository returns failure, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { profileRepository.setProfile(PROFILE) } returns Failure(Unspecified())
            coEvery { sessionRepository.openSession() } returns Success(Unit)

            `given tested use case`()

            // When
            val outcome = tested(profile = PROFILE, pin = 1234, currentBalance = 1234f)

            // Then
            outcome shouldEqual Failure(Unspecified())
        }

    @Test
    fun `when both session and profile repositories return failure, profile failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { profileRepository.setProfile(PROFILE) } returns Failure(Unspecified())
            coEvery { sessionRepository.openSession() } returns Failure(NoInternet)

            `given tested use case`()

            // When
            val outcome = tested(profile = PROFILE, pin = 1234, currentBalance = 1234f)

            // Then
            outcome shouldEqual Failure(Unspecified())
        }

    private fun `given tested use case`() {
        tested = SetupProfile(
            profileRepository = profileRepository,
            sessionRepository = sessionRepository,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val PROFILE = Profile(
    name = "Test",
    monthlyIncome = 1000,
    currency = EUR
)
