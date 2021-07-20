package com.nemscep.muffin.track

import com.nemscep.muffin.track.ui.topup.TrackTopupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trackModule = module {
    viewModel { TrackTopupViewModel(addTransaction = get(), getBalances = get()) }
}
