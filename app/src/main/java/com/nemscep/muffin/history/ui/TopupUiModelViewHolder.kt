package com.nemscep.muffin.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.databinding.ItemTopupBinding
import com.nemscep.muffin.history.ui.TransactionUiModel.TopupUiModel

class TopupUiModelViewHolder(
    private val itemTopupBinding: ItemTopupBinding,
    private val onTopupChipClicked: () -> Unit
) : TransactionViewHolder(itemTopupBinding.root) {
    override fun bind(transactionUiModel: TransactionUiModel) {
        val item = transactionUiModel as TopupUiModel
        with(itemTopupBinding) {
            tvTopupAmount.text = item.amount
            tvTopupTimestamp.text = item.date
            tvTopupTitle.text = item.description
            chipTopup.setOnClickListener { onTopupChipClicked() }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onTopupChipClicked: () -> Unit): TopupUiModelViewHolder {
            val binding = ItemTopupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopupUiModelViewHolder(binding, onTopupChipClicked)
        }
    }
}
