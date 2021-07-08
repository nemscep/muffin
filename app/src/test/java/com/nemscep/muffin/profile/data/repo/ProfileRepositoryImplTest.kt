package com.nemscep.muffin.profile.data.repo

import app.cash.turbine.test
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.data.datasources.ProfileDao
import com.nemscep.muffin.profile.data.models.ProfileEntity
import com.nemscep.muffin.profile.domain.entities.Currency.RSD
import com.nemscep.muffin.profile.domain.entities.Profile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class ProfileRepositoryImplTest {
    private val profileDao: ProfileDao = mockk(relaxed = true)
    private lateinit var tested: ProfileRepositoryImpl

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ExperimentalTime
    @Test
    fun `when db contains profile, it is emitted properly`() = testCoroutineRule.runBlockingTest {
        // Given
        every { profileDao.getProfile() } returns flowOf(PROFILE_ENTITY)
        `given tested use case`()

        // When

        // Then
        tested.getProfile().test {
            expectItem() shouldEqual PROFILE
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when db does not contain profile, nothing is emitted`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { profileDao.getProfile() } returns flowOf(null)
            `given tested use case`()

            // When

            // Then
            tested.getProfile().test {
                expectComplete()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db contains profile, logged in flow emits true`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { profileDao.getProfile() } returns flowOf(PROFILE_ENTITY)
            `given tested use case`()

            // When

            // Then
            tested.isLoggedIn.test {
                expectItem() shouldEqual true
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db does not contain profile, logged in flow emits false`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { profileDao.getProfile() } returns flowOf(null)
            `given tested use case`()

            // When

            // Then
            tested.isLoggedIn.test {
                expectItem() shouldEqual false
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when setProfile is called, insert in dao method is called`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { profileDao.insertProfile(PROFILE_ENTITY) } returns Unit
            `given tested use case`()

            // When
            tested.setProfile(PROFILE)

            // Then
            coVerify(exactly = 1) { profileDao.insertProfile(PROFILE_ENTITY) }
        }

    private fun `given tested use case`() {
        tested = ProfileRepositoryImpl(
            profileDao = profileDao,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val PROFILE = Profile(
    name = "Nemanja",
    monthlyIncome = 1000,
    currency = RSD
)

private val PROFILE_ENTITY = ProfileEntity(
    name = "Nemanja",
    monthlyIncome = 1000,
    currency = RSD
)
