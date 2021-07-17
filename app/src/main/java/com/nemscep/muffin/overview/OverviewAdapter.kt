package com.nemscep.muffin.overview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SpecificBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalancesHeader

class OverviewAdapter(private val onEditBalances: () -> Unit) :
    ListAdapter<OverviewItem, BalanceViewHolder>(OverviewDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder =
        when (viewType) {
            BALANCE_HEADER -> BalancesHeaderViewHolder.create(parent, onEditBalances)
            MAIN_BALANCE -> BalanceItemViewHolder.create(parent, {})
            SAVINGS_BALANCE -> BalanceItemViewHolder.create(parent, {})
            SPECIFIC_BALANCE -> BalanceItemViewHolder.create(parent, {})
            else -> TODO()
        }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is BalancesHeader -> BALANCE_HEADER
        is MainBalanceUiModel -> MAIN_BALANCE
        is SavingsBalanceUiModel -> SAVINGS_BALANCE
        is SpecificBalanceUiModel -> SPECIFIC_BALANCE
        else -> TODO()
    }
}

private const val BALANCE_HEADER = 0
private const val MAIN_BALANCE = 1
private const val SAVINGS_BALANCE = 2
private const val SPECIFIC_BALANCE = 3

object OverviewDiffUtil : DiffUtil.ItemCallback<OverviewItem>() {
    override fun areItemsTheSame(oldItem: OverviewItem, newItem: OverviewItem): Boolean =
        oldItem.isBalanceHeader() == newItem.isBalanceHeader() || oldItem.isMainBalanceUiModel() == newItem.isMainBalanceUiModel()
                || oldItem.isSavingsBalanceUiModel() == newItem.isSavingsBalanceUiModel() || oldItem.isSpecificBalanceUiModel() == newItem.isSpecificBalanceUiModel()

    override fun areContentsTheSame(oldItem: OverviewItem, newItem: OverviewItem): Boolean = when {
        oldItem is BalancesHeader && newItem is BalancesHeader -> true
        else -> oldItem == newItem
    }
}

private fun OverviewItem.isBalanceHeader() = this is BalancesHeader
private fun OverviewItem.isMainBalanceUiModel() = this is MainBalanceUiModel
private fun OverviewItem.isSavingsBalanceUiModel() = this is SavingsBalanceUiModel
private fun OverviewItem.isSpecificBalanceUiModel() = this is SpecificBalanceUiModel

sealed class BalanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(overviewItem: OverviewItem)
}
