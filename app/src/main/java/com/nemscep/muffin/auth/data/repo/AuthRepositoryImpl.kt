package com.nemscep.muffin.auth.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.CompositeFailure.Network.Unspecified
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.entities.toDomain
import com.nemscep.muffin.auth.domain.repo.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override fun getPin(): Flow<Pin> =
        dataStore.data.mapNotNull { preferences -> preferences[PIN_KEY]?.toDomain() }

    override suspend fun setPin(pin: Pin): Outcome<Unit, CompositeFailure<Nothing>> =
        withContext(ioDispatcher) {
            return@withContext try {
                dataStore.edit { preferences -> preferences[PIN_KEY] = pin.value.toString() }
                Success(Unit)
            } catch (e: Exception) {
                Failure(Unspecified(error = e))
            }
        }
}

internal val PIN_KEY = stringPreferencesKey("PIN")
