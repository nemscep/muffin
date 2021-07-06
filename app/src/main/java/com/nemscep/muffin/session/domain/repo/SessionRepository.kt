package com.nemscep.muffin.session.domain.repo

import com.nemscep.muffin.session.domain.entities.SessionState
import kotlinx.coroutines.flow.Flow

/**
 * Repository responsible for handling app session state storing and retrieving.
 */
interface SessionRepository {
    /**
     * Returns current [SessionState][Flow] object.
     */
    val sessionState: Flow<SessionState>

    /**
     * Opens new session.
     */
    suspend fun openSession()

    /**
     * Closes current session.
     */
    fun closeSession()
}
