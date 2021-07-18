package com.nemscep.muffin.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.MainBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SavingsBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SpecificBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalancesHeader
import com.nemscep.muffin.overview.OverviewItem.TransactionsOverviewHeader
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class OverviewViewModel(private val getBalances: GetBalances) : ViewModel() {
    val overviewItems: LiveData<List<OverviewItem>> = overviewItemsLiveData()

    private fun overviewItemsLiveData(): LiveData<List<OverviewItem>> = combine(
        flowOf(listOf(BalancesHeader)),
        getBalances().map { balances ->
            balances
                .filter { it.isVisibleInOverview }
                .map { it.toOverviewUiModel() }
        },
        flowOf(listOf(TransactionsOverviewHeader))
    ) { balancesHeader, balances, transactionsHeader -> balancesHeader + balances + transactionsHeader }.asLiveData()
}

/**
 * Sealed class representing overview list items.
 */
sealed class OverviewItem {
    object BalancesHeader : OverviewItem()
    sealed class BalanceOverviewUiModel : OverviewItem() {
        abstract val value: Float
        abstract val currency: String

        data class MainBalanceOverviewUiModel(
            override val value: Float,
            override val currency: String
        ) : BalanceOverviewUiModel()

        data class SavingsBalanceOverviewUiModel(
            override val value: Float,
            override val currency: String
        ) : BalanceOverviewUiModel()

        data class SpecificBalanceOverviewUiModel(
            val name: String,
            override val value: Float,
            override val currency: String
        ) : BalanceOverviewUiModel()
    }

    object TransactionsOverviewHeader : OverviewItem()
}

private fun Balance.toOverviewUiModel(): BalanceOverviewUiModel = when (this) {
    is MainBalance -> MainBalanceOverviewUiModel(value = value, currency = currency.name)
    is SavingsBalance -> SavingsBalanceOverviewUiModel(value = value, currency = currency.name)
    is SpecificBalance -> SpecificBalanceOverviewUiModel(
        value = value,
        currency = currency.name,
        name = name
    )
}
