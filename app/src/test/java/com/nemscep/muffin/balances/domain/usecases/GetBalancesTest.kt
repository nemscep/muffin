package com.nemscep.muffin.balances.domain.usecases

import app.cash.turbine.test
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.repo.BalancesRepository
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

class GetBalancesTest {
    private val balancesRepository: BalancesRepository = mockk(relaxed = true)
    private lateinit var tested: GetBalances

    @ExperimentalTime
    @Test
    fun `when repository has balances, they are emitted properly`() = runBlockingTest {
        // Given
        every { balancesRepository.getBalances() } returns flowOf(BALANCES)
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual BALANCES
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when repository has no balances, empty list is emitted properly`() = runBlockingTest {
        // Given
        every { balancesRepository.getBalances() } returns flowOf(emptyList())
        `given tested use case`()

        // When

        // Then
        tested().test {
            expectItem() shouldEqual emptyList()
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun `given tested use case`() {
        tested = GetBalances(balancesRepository = balancesRepository)
    }
}

private val MAIN_BALANCE = MainBalance(value = 1000f, currency = EUR)
private val SAVINGS_BALANCE = SavingsBalance(value = 1000f, currency = EUR)
private val SPECIFIC_BALANCE = SpecificBalance(name = "Allowance", value = 1000f, currency = EUR)
private val BALANCES = listOf(MAIN_BALANCE, SAVINGS_BALANCE, SPECIFIC_BALANCE)
