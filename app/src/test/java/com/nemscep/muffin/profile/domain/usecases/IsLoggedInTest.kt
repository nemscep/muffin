package com.nemscep.muffin.profile.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.profile.domain.repo.ProfileRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Assert.*
import org.junit.Test

class IsLoggedInTest {
    private val profileRepository: ProfileRepository = mockk(relaxed = true)
    private lateinit var tested: IsLoggedIn

    @ExperimentalTime
    @Test
    fun `when repository returns true, it is emitted properly`() = runBlockingTest {
        // Given
        val expected = true
        every { profileRepository.isLoggedIn } returns flowOf(expected)

        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual true
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when repository returns false, it is emitted properly`() = runBlockingTest {
        // Given
        val expected = false
        every { profileRepository.isLoggedIn } returns flowOf(expected)

        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual expected
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun `given tested use case`() {
        tested = IsLoggedIn(profileRepository = profileRepository)
    }
}
