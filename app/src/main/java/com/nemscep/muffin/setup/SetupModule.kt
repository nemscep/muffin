package com.nemscep.muffin.setup

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val setupModule = module {
    viewModel { SetupViewModel(setupProfile = get()) }
}
