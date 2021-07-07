package com.nemscep.muffin.session.data.repo

import com.nemscep.muffin.session.domain.entities.SessionState
import com.nemscep.muffin.session.domain.repo.SessionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Concrete implementation of [SessionRepository].
 */
class SessionRepositoryImpl : SessionRepository {

    override val sessionState: Flow<SessionState>
        get() = TODO("Not yet implemented")

    override suspend fun openSession() {
        TODO("Not yet implemented")
    }

    override fun closeSession() {
        TODO("Not yet implemented")
    }
}