package com.nemscep.muffin.session.data.repo

import com.nemscep.burrito.CompositeFailure
import com.nemscep.burrito.Outcome
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.session.domain.entities.SessionState
import com.nemscep.muffin.session.domain.repo.SessionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Concrete implementation of [SessionRepository].
 */
class SessionRepositoryImpl : SessionRepository {

    override val sessionState: Flow<SessionState>
        get() = TODO("Not yet implemented")

    override suspend fun openSession(): Outcome<Unit, CompositeFailure<Nothing>> {
        // TODO("Not yet implemented")
        return Success(Unit)
    }

    override fun closeSession() {
        TODO("Not yet implemented")
    }
}
