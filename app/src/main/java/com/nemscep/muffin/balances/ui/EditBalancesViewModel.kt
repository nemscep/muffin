package com.nemscep.muffin.balances.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.balances.domain.usecases.DeleteBalance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.balances.domain.usecases.UpdateIsVisibleInOverviewFlag
import com.nemscep.muffin.balances.ui.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SpecificBalanceUiModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditBalancesViewModel(
    getBalances: GetBalances,
    private val deleteBalance: DeleteBalance,
    private val updateIsVisibleInOverviewFlag: UpdateIsVisibleInOverviewFlag
) : ViewModel() {
    val balances: LiveData<List<BalanceUiModel>> = getBalances()
        .map { balances -> balances.map { it.toUiModel() } }
        .asLiveData()

    fun delete(balanceId: Int) {
        viewModelScope.launch {
            when (deleteBalance(balanceId = balanceId)) {
                is Success -> Unit // TODO(show success dialog)
                is Failure -> Unit // TODO(show failed dialog)
            }
        }
    }

    fun visibilityChanged(isChecked: Boolean, balanceId: Int) {
        viewModelScope.launch {
            when (updateIsVisibleInOverviewFlag(value = isChecked, balanceId = balanceId)) {
                is Success -> Unit // TODO(show success dialog)
                is Failure -> Unit // TODO(show failed dialog)
            }
        }
    }
}

/**
 * Sealed class representing [Balance] object ui model classes.
 */
sealed class BalanceUiModel {
    abstract val id: Int
    abstract val value: Float
    abstract val currency: String
    abstract val isVisibleInOverview: Boolean

    data class MainBalanceUiModel(
        override val id: Int,
        override val value: Float,
        override val currency: String,
        override val isVisibleInOverview: Boolean
    ) : BalanceUiModel()

    data class SavingsBalanceUiModel(
        override val id: Int,
        override val value: Float,
        override val currency: String,
        override val isVisibleInOverview: Boolean
    ) : BalanceUiModel()

    data class SpecificBalanceUiModel(
        override val id: Int,
        val name: String,
        override val value: Float,
        override val currency: String,
        override val isVisibleInOverview: Boolean
    ) : BalanceUiModel()
}

private fun Balance.toUiModel(): BalanceUiModel = when (this) {
    is MainBalance -> MainBalanceUiModel(
        value = value,
        currency = currency.name,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    is SavingsBalance -> SavingsBalanceUiModel(
        value = value,
        currency = currency.name,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
    is SpecificBalance -> SpecificBalanceUiModel(
        value = value,
        currency = currency.name,
        name = name,
        id = id,
        isVisibleInOverview = isVisibleInOverview
    )
}

