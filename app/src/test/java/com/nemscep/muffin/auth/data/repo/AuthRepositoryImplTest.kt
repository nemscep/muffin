package com.nemscep.muffin.auth.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import app.cash.turbine.test
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.entities.toEntity
import com.nemscep.muffin.common.TestCoroutineRule
import com.nemscep.muffin.common.runBlockingTest
import java.io.File
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.test.TestCoroutineScope
import org.amshove.kluent.shouldEqual
import org.junit.After
import org.junit.Rule
import org.junit.Test


class AuthRepositoryImplTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var tested: AuthRepositoryImpl

    private val tmpFile: File
        get() = File.createTempFile("some-prefix", ".preferences_pb")

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @After
    fun cleanUp() {
        tmpFile.delete()
    }

    @ExperimentalTime
    @Test
    fun `when pin exists in the data store, it is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val preferences = PreferenceDataStoreFactory
                .create(scope = TestCoroutineScope(testCoroutineRule.testDispatcher)) { tmpFile }
                .apply { edit { it[PIN_KEY] = PIN.toEntity() } }
            `given tested repository`(preferences)

            // When

            // Then
            tested.getPin().test {
                expectItem() shouldEqual PIN
                cancelAndIgnoreRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when pin is stored in the repository, it is emitted properly`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val preferences = PreferenceDataStoreFactory
                .create(scope = TestCoroutineScope(testCoroutineRule.testDispatcher)) { tmpFile }
            `given tested repository`(preferences)

            // When
            tested.setPin(PIN)

            // Then
            tested.getPin().test {
                expectItem() shouldEqual PIN
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun `given tested repository`(preferences: DataStore<Preferences>) {
        tested = AuthRepositoryImpl(
            dataStore = preferences,
            ioDispatcher = testCoroutineRule.testDispatcher
        )
    }
}

private val PIN = Pin(value = 1234)
