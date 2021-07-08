package com.nemscep.muffin.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.RSD
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.usecases.GetProfile
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class OverviewViewModelTest {
    private val getProfile: GetProfile = mockk(relaxed = true)
    private val profileObserver: Observer<Profile> = spyk()
    private lateinit var tested: OverviewViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `when profile value change, they are emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { getProfile() } returns flowOf(PROFILE_1, PROFILE_2, PROFILE_3)
            tested = OverviewViewModel(getProfile = getProfile)

            // When
            tested.profile.observeForever(profileObserver)

            // Then
            verifySequence {
                profileObserver.onChanged(PROFILE_1)
                profileObserver.onChanged(PROFILE_2)
                profileObserver.onChanged(PROFILE_3)
            }
        }
}

private val PROFILE_1 = Profile(
    name = "Nemanja",
    monthlyIncome = 1000,
    currency = RSD
)

private val PROFILE_2 = Profile(
    name = "Nemanja",
    monthlyIncome = 1000,
    currency = RSD
)

private val PROFILE_3 = Profile(
    name = "Nemanja",
    monthlyIncome = 5000,
    currency = RSD
)
