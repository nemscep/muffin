package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class UpdateIsVisibleInOverviewFlagTest {
    private val balancesRepository: BalancesRepository = mockk(relaxed = true)
    private lateinit var tested: UpdateIsVisibleInOverviewFlag

    @Test
    fun `when repository call is successful, success is returned`() = runBlockingTest {
        // Given
        coEvery {
            balancesRepository.changeIsVisibleInOverviewFlag(
                balanceId = 1,
                value = true
            )
        } returns Success(Unit)
        `given tested use case`()

        // When
        val outcome = tested(value = true, balanceId = 1)

        // Then
        outcome shouldEqual Success(Unit)
    }

    @Test
    fun `when repository call is unsuccessful, failure is returned`() = runBlockingTest {
        // Given
        coEvery {
            balancesRepository.changeIsVisibleInOverviewFlag(
                balanceId = 1,
                value = true
            )
        } returns Failure(Unspecified())
        `given tested use case`()

        // When
        val outcome = tested(value = true, balanceId = 1)

        // Then
        outcome shouldEqual Failure(Unspecified())
    }

    private fun `given tested use case`() {
        tested = UpdateIsVisibleInOverviewFlag(balancesRepository = balancesRepository)
    }
}
