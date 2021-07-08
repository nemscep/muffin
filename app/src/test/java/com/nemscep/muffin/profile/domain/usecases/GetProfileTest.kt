package com.nemscep.muffin.profile.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.profile.domain.entities.Currency.RSD
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class GetProfileTest {
    private val profileRepository: ProfileRepository = mockk(relaxed = true)
    private lateinit var tested: GetProfile

    @ExperimentalTime
    @Test
    fun `when repository has profile it is emitted properly`() = runBlockingTest {
        // Given
        every { profileRepository.getProfile() } returns flowOf(PROFILE)
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual PROFILE
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun `given tested use case`() {
        tested = GetProfile(profileRepository = profileRepository)
    }
}

private val PROFILE = Profile(
    name = "Nemanja",
    monthlyIncome = 1000,
    currency = RSD
)
