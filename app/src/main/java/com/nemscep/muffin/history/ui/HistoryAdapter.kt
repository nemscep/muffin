package com.nemscep.muffin.history.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nemscep.muffin.history.ui.TransactionUiModel.ExpenseUiModel
import com.nemscep.muffin.history.ui.TransactionUiModel.TopupUiModel
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory

class HistoryAdapter(
    private val onExpenseCategoryClicked: (expenseCategory: ExpenseCategory) -> Unit,
    private val onTopupChipClicked: () -> Unit
) : ListAdapter<TransactionUiModel, TransactionViewHolder>(TransactionDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        when (viewType) {
            EXPENSE -> ExpenseUiModelViewHolder.create(
                parent = parent,
                onCategoryClicked = onExpenseCategoryClicked
            )
            TOPUP -> TopupUiModelViewHolder.create(
                parent = parent,
                onTopupChipClicked = onTopupChipClicked
            )
            else -> TODO()
        }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ExpenseUiModel -> EXPENSE
        is TopupUiModel -> TOPUP
    }
}

private const val EXPENSE = 0
private const val TOPUP = 1

sealed class TransactionViewHolder(val view: View) : ViewHolder(view) {
    abstract fun bind(transactionUiModel: TransactionUiModel)
}

object TransactionDiffUtil : DiffUtil.ItemCallback<TransactionUiModel>() {
    override fun areItemsTheSame(
        oldItem: TransactionUiModel,
        newItem: TransactionUiModel
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: TransactionUiModel,
        newItem: TransactionUiModel
    ): Boolean = oldItem == newItem

}


