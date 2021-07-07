package com.nemscep.muffin.session

import com.nemscep.muffin.session.data.repo.SessionRepositoryImpl
import com.nemscep.muffin.session.domain.repo.SessionRepository
import com.nemscep.muffin.session.domain.usecases.GetSessionState
import org.koin.dsl.module

val sessionModule = module {
    single<SessionRepository> { SessionRepositoryImpl() }
    factory { GetSessionState(sessionRepository = get()) }
}
