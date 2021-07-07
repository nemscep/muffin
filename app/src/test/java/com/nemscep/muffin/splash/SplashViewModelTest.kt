package com.nemscep.muffin.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.usecases.IsLoggedIn
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {
    private val isLoggedIn: IsLoggedIn = mockk(relaxed = true)
    private val isLoggedInObserver: Observer<Boolean> = spyk()

    private lateinit var tested: SplashViewModel

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when logged in state changes, values are emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { isLoggedIn() } returns flowOf(true, false, true)
            tested = SplashViewModel(isLoggedIn = isLoggedIn)

            // When
            tested.isLoggedIn.observeForever(isLoggedInObserver)

            // Then
            verifySequence {
                isLoggedInObserver.onChanged(true)
                isLoggedInObserver.onChanged(false)
                isLoggedInObserver.onChanged(true)
            }
        }
}
