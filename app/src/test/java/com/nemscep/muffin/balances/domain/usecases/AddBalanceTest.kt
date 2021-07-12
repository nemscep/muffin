package com.nemscep.muffin.balances.domain.usecases

import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class AddBalanceTest {
    private val balancesRepository: BalancesRepository = mockk(relaxed = true)
    private lateinit var tested: AddBalance

    @Test
    fun `when adding balance is successful, success is returned`() = runBlockingTest {
        // Given
        val expected = Success(Unit)
        coEvery { balancesRepository.addBalance(MAIN_BALANCE) } returns expected
        `given tested use case`()

        // When
        val outcome = tested(MAIN_BALANCE)

        // Then
        outcome shouldEqual expected
    }

    @Test
    fun `when adding balance is unsuccessful, failure is returned`() = runBlockingTest {
        // Given
        val expected = Failure(Unspecified(error = Exception()))
        coEvery { balancesRepository.addBalance(MAIN_BALANCE) } returns expected
        `given tested use case`()

        // When
        val outcome = tested(MAIN_BALANCE)

        // Then
        outcome shouldEqual expected
    }

    private fun `given tested use case`() {
        tested = AddBalance(balancesRepository = balancesRepository)
    }
}

private val MAIN_BALANCE = MainBalance(value = 1000, currency = EUR)
