package com.nemscep.muffin.balances.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nemscep.muffin.balances.ui.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SpecificBalanceUiModel

class BalancesAdapter(
    private val onDeleteBalance: (balanceId: Int) -> Unit,
    private val onEditBalance: (balanceId: Int) -> Unit,
    private val onVisibleInOverviewChange: (isChecked: Boolean, balanceId: Int) -> Unit
) : ListAdapter<BalanceUiModel, BalanceViewHolder>(BalanceUiModelDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder =
        when (viewType) {
            MAIN_BALANCE -> BalanceViewHolder.create(
                parent = parent,
                isDeletable = false,
                onDelete = {},
                onEdit = onEditBalance,
                onVisibleInOverviewChange = onVisibleInOverviewChange
            )
            SAVINGS_BALANCE -> BalanceViewHolder.create(
                parent = parent,
                isDeletable = true,
                onDelete = onDeleteBalance,
                onEdit = onEditBalance,
                onVisibleInOverviewChange = onVisibleInOverviewChange
            )
            SPECIFIC_BALANCE -> BalanceViewHolder.create(
                parent = parent,
                isDeletable = true,
                onDelete = onDeleteBalance,
                onEdit = onEditBalance,
                onVisibleInOverviewChange = onVisibleInOverviewChange
            )
            else -> TODO()
        }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MainBalanceUiModel -> MAIN_BALANCE
        is SavingsBalanceUiModel -> SAVINGS_BALANCE
        is SpecificBalanceUiModel -> SPECIFIC_BALANCE
        else -> TODO()
    }
}

private const val MAIN_BALANCE = 1
private const val SAVINGS_BALANCE = 2
private const val SPECIFIC_BALANCE = 3

object BalanceUiModelDiffUtil : DiffUtil.ItemCallback<BalanceUiModel>() {
    override fun areItemsTheSame(oldItem: BalanceUiModel, newItem: BalanceUiModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: BalanceUiModel, newItem: BalanceUiModel): Boolean =
        oldItem == newItem
}
