package com.nemscep.muffin.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nemscep.muffin.databinding.ItemExpenseBinding
import com.nemscep.muffin.history.ui.TransactionUiModel.ExpenseUiModel
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory

class ExpenseUiModelViewHolder(
    private val itemExpenseBinding: ItemExpenseBinding,
    private val onCategoryClicked: (expenseCategory: ExpenseCategory) -> Unit
) : TransactionViewHolder(itemExpenseBinding.root) {
    override fun bind(transactionUiModel: TransactionUiModel) {
        val item = transactionUiModel as ExpenseUiModel
        with(itemExpenseBinding) {
            tvExpenseAmount.text = item.amount
            tvExpenseDescription.text = item.description
            tvExpenseTimestamp.text = item.date
            chipCategory.text = item.expenseCategory.name
            chipCategory.setOnClickListener { onCategoryClicked(item.expenseCategory) }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onCategoryClicked: (expenseCategory: ExpenseCategory) -> Unit
        ): ExpenseUiModelViewHolder {
            val binding = ItemExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ExpenseUiModelViewHolder(binding, onCategoryClicked)
        }
    }
}
