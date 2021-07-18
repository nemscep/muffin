package com.nemscep.muffin.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ItemBalanceBinding
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.overview.OverviewItem.BalanceUiModel.SpecificBalanceUiModel

data class BalanceItemViewHolder(
    private val itemBalanceBinding: ItemBalanceBinding,
    private val openDetails: (String) -> Unit
) : OverviewViewHolder(itemBalanceBinding.root) {

    private val mainBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.main_balance) }
    private val savingsBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.savings_balance) }

    override fun bind(overviewItem: OverviewItem) {
        val balanceItem = overviewItem as BalanceUiModel
        with(itemBalanceBinding) {
            val balanceName = when (balanceItem) {
                is MainBalanceUiModel -> mainBalanceText
                is SavingsBalanceUiModel -> savingsBalanceText
                is SpecificBalanceUiModel -> balanceItem.name
            }
            tvBalanceName.text = balanceName
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
            return BalanceItemViewHolder(binding, openDetails)
        }
    }
}
