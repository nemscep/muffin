package com.nemscep.muffin.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.databinding.ItemTransactionsOverviewHeaderBinding

data class TransactionsOverviewHeaderViewHolder(
    private val itemTransactionsOverviewHeaderBinding: ItemTransactionsOverviewHeaderBinding,
    private val onViewMore: () -> Unit
) : OverviewViewHolder(itemTransactionsOverviewHeaderBinding.root) {

    override fun bind(overviewItem: OverviewItem) {
        with(itemTransactionsOverviewHeaderBinding) {
            btnViewTransactions.setOnClickListener { onViewMore() }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onViewMore: () -> Unit): OverviewViewHolder {
            val binding = ItemTransactionsOverviewHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TransactionsOverviewHeaderViewHolder(binding, onViewMore)
        }
    }
}
