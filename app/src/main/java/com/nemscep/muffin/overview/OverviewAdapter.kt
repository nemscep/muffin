package com.nemscep.muffin.overview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.MainBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SavingsBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SpecificBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalancesHeader
import com.nemscep.muffin.overview.OverviewItem.TransactionsOverviewHeader

class OverviewAdapter(
    private val onEditBalances: () -> Unit,
    private val onViewTransactionsOverview: () -> Unit
) : ListAdapter<OverviewItem, OverviewViewHolder>(OverviewDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder =
        when (viewType) {
            BALANCE_HEADER -> BalancesOverviewHeaderViewHolder.create(parent, onEditBalances)
            MAIN_BALANCE -> BalanceOverviewItemViewHolder.create(parent, {})
            SAVINGS_BALANCE -> BalanceOverviewItemViewHolder.create(parent, {})
            SPECIFIC_BALANCE -> BalanceOverviewItemViewHolder.create(parent, {})
            TRANSACTIONS_OVERVIEW_HEADER -> {
                TransactionsOverviewHeaderViewHolder.create(
                    parent,
                    onViewTransactionsOverview
                )
            }
            else -> TODO()
        }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is BalancesHeader -> BALANCE_HEADER
        is MainBalanceOverviewUiModel -> MAIN_BALANCE
        is SavingsBalanceOverviewUiModel -> SAVINGS_BALANCE
        is SpecificBalanceOverviewUiModel -> SPECIFIC_BALANCE
        is TransactionsOverviewHeader -> TRANSACTIONS_OVERVIEW_HEADER
        else -> TODO()
    }
}

private const val BALANCE_HEADER = 0
private const val MAIN_BALANCE = 1
private const val SAVINGS_BALANCE = 2
private const val SPECIFIC_BALANCE = 3
private const val TRANSACTIONS_OVERVIEW_HEADER = 4

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
private fun OverviewItem.isMainBalanceUiModel() = this is MainBalanceOverviewUiModel
private fun OverviewItem.isSavingsBalanceUiModel() = this is SavingsBalanceOverviewUiModel
private fun OverviewItem.isSpecificBalanceUiModel() = this is SpecificBalanceOverviewUiModel

sealed class OverviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(overviewItem: OverviewItem)
}
