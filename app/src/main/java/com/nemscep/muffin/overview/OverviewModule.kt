package com.nemscep.muffin.overview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val overviewModule = module {
    viewModel { OverviewViewModel(getProfile = get()) }
}
