package com.nemscep.muffin.session.domain.usecases

import com.nemscep.muffin.session.domain.entities.SessionState
import com.nemscep.muffin.session.domain.repo.SessionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for retrieving current session state.
 */
class GetSessionState(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<SessionState> = sessionRepository.sessionState
}
