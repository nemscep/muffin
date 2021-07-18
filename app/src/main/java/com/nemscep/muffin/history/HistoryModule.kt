package com.nemscep.muffin.history

import com.nemscep.muffin.history.ui.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    viewModel { HistoryViewModel() }
}
