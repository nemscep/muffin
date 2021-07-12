package com.nemscep.muffin.balances.data.repo

import app.cash.turbine.test
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.data.datasources.BalanceDao
import com.nemscep.muffin.balances.data.models.toEntity
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class BalancesRepositoryImplTest {
    private val balanceDao: BalanceDao = mockk(relaxed = true)
    private lateinit var tested: BalancesRepositoryImpl

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ExperimentalTime
    @Test
    fun `when db contains balances, they are emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { balanceDao.getBalances() } returns flowOf(BALANCES_ENTITIES)
            `given tested use case`()

            // When

            // Then
            tested.getBalances().test {
                expectItem() shouldEqual BALANCES
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db does not contain balances, empty list is emitted`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { balanceDao.getBalances() } returns flowOf(emptyList())
            `given tested use case`()

            // When

            // Then
            tested.getBalances().test {
                expectItem() shouldEqual emptyList()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db contains main balance, it is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { balanceDao.getBalances() } returns flowOf(BALANCES_ENTITIES)
            `given tested use case`()

            // When

            // Then
            tested.getMainBalance().test {
                expectItem() shouldEqual MAIN_BALANCE
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when db contains savings balance, it is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            every { balanceDao.getBalances() } returns flowOf(BALANCES_ENTITIES)
            `given tested use case`()

            // When

            // Then
            tested.getSavingsBalance().test {
                expectItem() shouldEqual SAVINGS_BALANCE
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when add balance is successful, success is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { balanceDao.insertBalance(MAIN_BALANCE.toEntity()) } returns Unit
            `given tested use case`()

            // When
            val outcome = tested.addBalance(MAIN_BALANCE)

            // Then
            outcome shouldEqual Success(Unit)
        }

    @Test
    fun `when add balance is unsuccessful, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val exception = Exception()
            coEvery { balanceDao.insertBalance(MAIN_BALANCE.toEntity()) } coAnswers { throw exception }
            `given tested use case`()

            // When
            val outcome = tested.addBalance(MAIN_BALANCE)

            // Then
            outcome shouldEqual Failure(Unspecified(error = exception))
        }

    @Test
    fun `when balance deletion is successful, success is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            coEvery { balanceDao.deleteBalance(MAIN_BALANCE.toEntity()) } returns Unit
            `given tested use case`()

            // When
            val outcome = tested.deleteBalance(MAIN_BALANCE)

            // Then
            outcome shouldEqual Success(Unit)
        }

    @Test
    fun `when balance deletion is unsuccessful, failure is returned`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val exception = Exception()
            coEvery { balanceDao.deleteBalance(MAIN_BALANCE.toEntity()) } coAnswers { throw exception }
            `given tested use case`()

            // When
            val outcome = tested.deleteBalance(MAIN_BALANCE)

            // Then
            outcome shouldEqual Failure(Unspecified(error = exception))
        }

    private fun `given tested use case`() {
        tested = BalancesRepositoryImpl(
            balanceDao = balanceDao,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val MAIN_BALANCE = MainBalance(value = 1000, currency = EUR)
private val SAVINGS_BALANCE = SavingsBalance(value = 1000, currency = EUR)
private val SPECIFIC_BALANCE = SpecificBalance(name = "Allowance", value = 1000, currency = EUR)
private val BALANCES = listOf(MAIN_BALANCE, SAVINGS_BALANCE, SPECIFIC_BALANCE)
private val BALANCES_ENTITIES = listOf(
    MAIN_BALANCE.toEntity(),
    SAVINGS_BALANCE.toEntity(),
    SPECIFIC_BALANCE.toEntity()
)
