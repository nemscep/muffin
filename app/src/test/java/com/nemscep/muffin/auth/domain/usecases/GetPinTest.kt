package com.nemscep.muffin.auth.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.repo.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class GetPinTest {
    private val authRepository: AuthRepository = mockk(relaxed = true)
    private lateinit var tested: GetPin

    @ExperimentalTime
    @Test
    fun `when repository has pin, it is returned properly`() = runBlockingTest {
        // Given
        coEvery { authRepository.getPin() } returns flowOf(PIN)
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual PIN
            cancelAndIgnoreRemainingEvents()
        }

    }

    private fun `given tested use case`() {
        tested = GetPin(authRepository = authRepository)
    }
}

private val PIN = Pin(value = 1234)
