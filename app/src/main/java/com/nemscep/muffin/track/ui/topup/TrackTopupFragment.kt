package com.nemscep.muffin.track.ui.topup

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
import com.nemscep.muffin.databinding.FragmentTrackTopupBinding
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingFailed
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingSuccessful
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class TrackTopupFragment : Fragment(R.layout.fragment_track_topup) {
    private val binding by viewBinding(FragmentTrackTopupBinding::bind)
    private val viewModel by viewModel<TrackTopupViewModel>()

    private val topupText by lazy { getString(R.string.topup) }
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

    private fun FragmentTrackTopupBinding.setupLiveData() {
        with(viewModel) {
            events.observe(viewLifecycleOwner) { event ->
                when (event) {
                    TrackingFailed -> TODO()
                    TrackingSuccessful -> requireActivity().onBackPressed()
                }
            }
            balances.observe(viewLifecycleOwner) { setupBalanceDropdown(it) }
        }
    }

    private fun FragmentTrackTopupBinding.setupBalanceDropdown(balances: List<Balance>) {
        val adapter = ArrayAdapter(
            requireContext(),
            layout.item_balance_name,
            balances.map { it.getBalanceName() })
        (tilTopupBalance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun Balance.getBalanceName() = when (this) {
        is MainBalance -> mainBalanceText
        is SavingsBalance -> savingsBalanceText
        is SpecificBalance -> name
    }

    private fun FragmentTrackTopupBinding.setupDatePicker() {
        datePicker.addOnPositiveButtonClickListener { time ->
            acTopupDate.setText(dateFormat.format(Date(time)))
        }
        acTopupDate.setText(dateFormat.format(Date()))
    }

    private fun FragmentTrackTopupBinding.setupViews() {
        tilTopupDescription.editText?.addTextChangedListener {
            tilTopupDescription.error = ""
        }
        tilTopupAmount.editText?.addTextChangedListener {
            tilTopupAmount.error = ""
        }
        tilTopupBalance.editText?.addTextChangedListener {
            tilTopupBalance.error = ""
        }
        acTopupDate.setOnClickListener {
            if (childFragmentManager.findFragmentByTag("topup-picker") == null) {
                datePicker.show(childFragmentManager, "topup-picker")
            }
        }
        btnTrackTopup.setOnClickListener {
            val amount = tilTopupAmount.editText?.text.toString().toFloatOrNull()
            val description = tilTopupDescription.editText?.text.toString()
                .let { if (it.isNotBlank()) it else topupText }
            val date = tilTopupDate.editText?.text.toString()
                .let { dateFormat.parse(it) ?: Date() }
            val balanceText = (tilTopupBalance.editText as? AutoCompleteTextView)?.text.toString()
            val balance = viewModel.balances.value
                ?.firstOrNull {
                    it is MainBalance && balanceText == mainBalanceText
                            || it is SavingsBalance && balanceText == savingsBalanceText
                            || it is SpecificBalance && balanceText == it.name
                }

            if (amount == null) tilTopupAmount.error = requiredFieldText
            if (balance == null) tilTopupBalance.error = requiredFieldText
            if (amount != null && balance != null) {
                viewModel.trackTopup(
                    amount = amount,
                    date = date,
                    description = description,
                    balance = balance
                )
            }
        }
    }
}

private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
