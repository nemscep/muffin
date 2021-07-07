package com.nemscep.muffin.session.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.session.domain.entities.SessionState.Expired
import com.nemscep.muffin.session.domain.entities.SessionState.InProgress
import com.nemscep.muffin.session.domain.repo.SessionRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class GetSessionStateTest {
    private val sessionRepository: SessionRepository = mockk(relaxed = true)
    private lateinit var tested: GetSessionState

    @ExperimentalTime
    @Test
    fun `when repository has in progress state, it is emitted properly`() = runBlockingTest {
        // Given
        val expected = InProgress
        every { sessionRepository.sessionState } returns flowOf(expected)

        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual expected
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when repository has expired state, it is emitted properly`() = runBlockingTest {
        // Given
        val expected = Expired
        every { sessionRepository.sessionState } returns flowOf(expected)

        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual expected
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun `given tested use case`() {
        tested = GetSessionState(sessionRepository = sessionRepository)
    }

}
