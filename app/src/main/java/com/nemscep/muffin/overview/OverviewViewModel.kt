package com.nemscep.muffin.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SpecificBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalancesHeader
import com.nemscep.muffin.profile.domain.usecases.GetProfile
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class OverviewViewModel(
    getProfile: GetProfile,
    private val getBalances: GetBalances
) : ViewModel() {
    val profile = getProfile().asLiveData()
    val balances = getBalances().asLiveData()
    val overviewItems: LiveData<List<OverviewItem>> = overviewItemsLiveData()

    private fun overviewItemsLiveData(): LiveData<List<OverviewItem>> = combine(
        flowOf(listOf(BalancesHeader)),
        getBalances().map { balances -> balances.map { it.toUiModel() } }
    ) { header, balances -> header + balances }.asLiveData()
}

/**
 * Sealed class representing overview list items.
 */
sealed class OverviewItem {
    object BalancesHeader : OverviewItem()
    sealed class BalanceUiModel : OverviewItem() {
        abstract val value: Float
        abstract val currency: String

        data class MainBalanceUiModel(
            override val value: Float,
            override val currency: String
        ) : BalanceUiModel()

        data class SavingsBalanceUiModel(
            override val value: Float,
            override val currency: String
        ) : BalanceUiModel()

        data class SpecificBalanceUiModel(
            val name: String,
            override val value: Float,
            override val currency: String
        ) : BalanceUiModel()
    }
}

fun Balance.toUiModel(): BalanceUiModel = when (this) {
    is MainBalance -> MainBalanceUiModel(value = value, currency = currency.name)
    is SavingsBalance -> SavingsBalanceUiModel(value = value, currency = currency.name)
    is SpecificBalance -> SpecificBalanceUiModel(
        value = value,
        currency = currency.name,
        name = name
    )
}
