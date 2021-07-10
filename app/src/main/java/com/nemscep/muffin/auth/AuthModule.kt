package com.nemscep.muffin.auth

import com.nemscep.muffin.auth.data.repo.AuthRepositoryImpl
import com.nemscep.muffin.auth.domain.repo.AuthRepository
import com.nemscep.muffin.auth.domain.usecases.GetPin
import com.nemscep.muffin.auth.domain.usecases.VerifyPin
import com.nemscep.muffin.auth.ui.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { AuthViewModel(verifyPin = get()) }
    single<AuthRepository> { AuthRepositoryImpl(dataStore = get()) }
    factory { GetPin(authRepository = get()) }
    factory { VerifyPin(getPin = get()) }
}
