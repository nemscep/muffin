package com.nemscep.muffin.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ItemBalanceBinding
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.MainBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SavingsBalanceOverviewUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceOverviewUiModel.SpecificBalanceOverviewUiModel

data class BalanceOverviewItemViewHolder(
    private val itemBalanceBinding: ItemBalanceBinding,
    private val openDetails: (String) -> Unit
) : OverviewViewHolder(itemBalanceBinding.root) {

    private val mainBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.main_balance) }
    private val savingsBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.savings_balance) }

    override fun bind(overviewItem: OverviewItem) {
        val balanceItem = overviewItem as BalanceOverviewUiModel
        with(itemBalanceBinding) {
            tvBalanceName.text = when (balanceItem) {
                is MainBalanceOverviewUiModel -> mainBalanceText
                is SavingsBalanceOverviewUiModel -> savingsBalanceText
                is SpecificBalanceOverviewUiModel -> balanceItem.name
            }
            tvBalanceAmount.text = "${balanceItem.value} ${balanceItem.currency}"
        }
    }

    companion object {
        fun create(parent: ViewGroup, openDetails: (String) -> Unit): OverviewViewHolder {
            val binding = ItemBalanceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BalanceOverviewItemViewHolder(binding, openDetails)
        }
    }
}
