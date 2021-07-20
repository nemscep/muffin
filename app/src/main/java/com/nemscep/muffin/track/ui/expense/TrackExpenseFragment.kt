package com.nemscep.muffin.track.ui.expense

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SavingsBalance
import com.nemscep.muffin.balances.domain.entities.Balance.SpecificBalance
import com.nemscep.muffin.databinding.FragmentTrackExpenseBinding
import com.nemscep.muffin.track.ui.expense.TrackExpenseEvents.ExpenseTrackingFailed
import com.nemscep.muffin.track.ui.expense.TrackExpenseEvents.ExpenseTrackingSuccessful
import com.nemscep.muffin.transactions.domain.entities.ExpenseCategory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class TrackExpenseFragment : Fragment(R.layout.fragment_track_expense) {
    private val binding by viewBinding(FragmentTrackExpenseBinding::bind)
    private val viewModel by viewModel<TrackExpenseViewModel>()

    private val requiredFieldText by lazy { getString(R.string.field_required) }
    private val mainBalanceText by lazy { getString(R.string.main_balance) }
    private val savingsBalanceText by lazy { getString(R.string.savings_balance) }

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupDatePicker()
        binding.setupViews()
        binding.setupLiveData()
    }

    private fun FragmentTrackExpenseBinding.setupLiveData() {
        with(viewModel) {
            events.observe(viewLifecycleOwner) { event ->
                when (event) {
                    ExpenseTrackingFailed -> TODO()
                    ExpenseTrackingSuccessful -> requireActivity().onBackPressed()
                }
            }
            balances.observe(viewLifecycleOwner) { setupBalanceDropdown(it) }
            expenseCategories.observe(viewLifecycleOwner) { setupCategoriesDropdown(it) }
        }
    }

    private fun FragmentTrackExpenseBinding.setupBalanceDropdown(balances: List<Balance>) {
        val adapter = ArrayAdapter(
            requireContext(),
            layout.item_balance_name,
            balances.map { it.getBalanceName() })
        (tilExpenseBalance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun FragmentTrackExpenseBinding.setupCategoriesDropdown(categories: List<ExpenseCategory>) {
        val adapter = ArrayAdapter(
            requireContext(),
            layout.item_expense_category_name,
            categories
        )
        (tilExpenseCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun Balance.getBalanceName() = when (this) {
        is MainBalance -> mainBalanceText
        is SavingsBalance -> savingsBalanceText
        is SpecificBalance -> name
    }

    private fun FragmentTrackExpenseBinding.setupDatePicker() {
        datePicker.addOnPositiveButtonClickListener { time ->
            acExpenseDate.setText(dateFormat.format(Date(time)))
        }
        acExpenseDate.setText(dateFormat.format(Date()))
    }

    private fun FragmentTrackExpenseBinding.setupViews() {
        tilExpenseDescription.editText?.addTextChangedListener {
            tilExpenseDescription.error = ""
        }
        tilExpenseAmount.editText?.addTextChangedListener {
            tilExpenseAmount.error = ""
        }
        tilExpenseBalance.editText?.addTextChangedListener {
            tilExpenseBalance.error = ""
        }
        tilExpenseCategory.editText?.addTextChangedListener {
            tilExpenseCategory.error = ""
        }
        acExpenseDate.setOnClickListener {
            if (childFragmentManager.findFragmentByTag("Expense-picker") == null) {
                datePicker.show(childFragmentManager, "Expense-picker")
            }
        }
        btnTrackExpense.setOnClickListener {
            val amount = tilExpenseAmount.editText?.text.toString().toFloatOrNull()
            val description = tilExpenseDescription.editText?.text.toString()
            val date = tilExpenseDate.editText?.text.toString()
                .let { dateFormat.parse(it) ?: Date() }
            val balanceText = (tilExpenseBalance.editText as? AutoCompleteTextView)?.text.toString()
            val balance = viewModel.balances.value
                ?.firstOrNull {
                    it is MainBalance && balanceText == mainBalanceText
                            || it is SavingsBalance && balanceText == savingsBalanceText
                            || it is SpecificBalance && balanceText == it.name
                }
            val expenseCategoryText = tilExpenseCategory.editText?.text.toString()
            val expenseCategory = viewModel.expenseCategories.value
                ?.firstOrNull { it.name == expenseCategoryText }

            if (amount == null) tilExpenseAmount.error = requiredFieldText
            if (expenseCategory == null) tilExpenseCategory.error = requiredFieldText
            if (balance == null) tilExpenseBalance.error = requiredFieldText
            if (description.isEmpty()) tilExpenseDescription.error = requiredFieldText
            if (amount != null && balance != null && expenseCategory != null) {
                viewModel.trackExpense(
                    amount = amount,
                    date = date,
                    description = description,
                    balance = balance,
                    expenseCategory = expenseCategory
                )
            }
        }
    }
}

private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
