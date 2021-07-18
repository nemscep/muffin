package com.nemscep.muffin.balances.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nemscep.muffin.R
import com.nemscep.muffin.balances.ui.BalanceUiModel.MainBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SavingsBalanceUiModel
import com.nemscep.muffin.balances.ui.BalanceUiModel.SpecificBalanceUiModel
import com.nemscep.muffin.databinding.ItemBalanceBinding

class BalanceViewHolder(
    private val itemBalanceBinding: ItemBalanceBinding,
    private val isDeletable: Boolean,
    private val onDelete: (balanceId: Int) -> Unit,
    private val onEdit: (balanceId: Int) -> Unit,
    private val onVisibleInOverviewChange: (isChecked: Boolean, balanceId: Int) -> Unit
) : ViewHolder(itemBalanceBinding.root) {

    private val mainBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.main_balance) }
    private val savingsBalanceText by lazy { itemBalanceBinding.root.context.getString(R.string.savings_balance) }

    fun bind(balanceUiModel: BalanceUiModel) {
        with(itemBalanceBinding) {
            // Deletion
            btnDeleteBalance.isVisible = isDeletable
            btnDeleteBalance.setOnClickListener { onDelete(balanceUiModel.id) }
            // Editing
            btnEditBalance.setOnClickListener { onEdit(balanceUiModel.id) }
            // Values
            tvBalanceAmount.text = "${balanceUiModel.value} ${balanceUiModel.currency}"
            tvBalanceName.text = when (balanceUiModel) {
                is MainBalanceUiModel -> mainBalanceText
                is SavingsBalanceUiModel -> savingsBalanceText
                is SpecificBalanceUiModel -> balanceUiModel.name
            }
            // Switch
            swVisibleInOverview.isChecked = balanceUiModel.isVisibleInOverview
            swVisibleInOverview.setOnCheckedChangeListener { _, isChecked ->
                onVisibleInOverviewChange(isChecked, balanceUiModel.id)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            isDeletable: Boolean,
            onDelete: (balanceId: Int) -> Unit,
            onEdit: (balanceId: Int) -> Unit,
            onVisibleInOverviewChange: (isChecked: Boolean, balanceId: Int) -> Unit
        ): BalanceViewHolder {
            val binding = ItemBalanceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BalanceViewHolder(
                itemBalanceBinding = binding,
                isDeletable = isDeletable,
                onDelete = onDelete,
                onVisibleInOverviewChange = onVisibleInOverviewChange,
                onEdit = onEdit
            )
        }
    }
}
