package com.nemscep.muffin.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.ItemBalancesHeaderBinding

data class BalancesOverviewHeaderViewHolder(
    private val itemBalancesHeaderBinding: ItemBalancesHeaderBinding,
    private val onEditBalances: () -> Unit
) : OverviewViewHolder(itemBalancesHeaderBinding.root) {

    private val headerText by lazy { itemBalancesHeaderBinding.root.resources.getString(R.string.my_balances) }

    override fun bind(overviewItem: OverviewItem) {
        with(itemBalancesHeaderBinding) {
            tvBalancesHeader.text = headerText
            btnEditBalances.setOnClickListener { onEditBalances() }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onEditBalances: () -> Unit): OverviewViewHolder {
            val binding = ItemBalancesHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BalancesOverviewHeaderViewHolder(binding, onEditBalances)
        }
    }
}
