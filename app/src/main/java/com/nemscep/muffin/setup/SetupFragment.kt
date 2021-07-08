package com.nemscep.muffin.setup

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.databinding.FragmentSetupBinding
import com.nemscep.muffin.profile.domain.entities.Currency
import com.nemscep.muffin.profile.domain.entities.Currency.EUR
import com.nemscep.muffin.profile.domain.entities.Currency.RSD
import com.nemscep.muffin.profile.domain.entities.Currency.SWISS_FRANC
import com.nemscep.muffin.profile.domain.entities.Currency.US_DOLLAR
import com.nemscep.muffin.setup.SetupEvents.NavigateToDashboard
import com.nemscep.muffin.setup.SetupEvents.SetupFailed
import org.koin.androidx.viewmodel.ext.android.viewModel
import viewBinding

class SetupFragment : Fragment(R.layout.fragment_setup) {
    private val binding by viewBinding(FragmentSetupBinding::bind)
    private val viewModel by viewModel<SetupViewModel>()

    private val requiredError by lazy { getString(R.string.field_required) }
    private val currencies by lazy { resources.getStringArray(R.array.currencies) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupCurrencyDropdown()
            setupViews()
            setupLiveData()
        }
    }

    private fun FragmentSetupBinding.setupLiveData() {
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                NavigateToDashboard -> handleNavigateToDashboard()
                SetupFailed -> handleSetupFailed()
            }
        }
    }

    private fun FragmentSetupBinding.handleNavigateToDashboard() {
        findNavController().navigate(SetupFragmentDirections.actionGlobalDashboardFragment())
    }

    private fun FragmentSetupBinding.handleSetupFailed() {
        // TODO show setup failed error
    }

    private fun FragmentSetupBinding.setupViews() {
        tilName.editText?.addTextChangedListener {
            tilName.error = ""
        }
        tilCurrency.editText?.addTextChangedListener {
            tilCurrency.error = ""
        }
        tilIncome.editText?.addTextChangedListener {
            tilIncome.error = ""
        }
        tilPin.editText?.addTextChangedListener {
            tilPin.error = ""
        }
        tilCurrentBalance.editText?.addTextChangedListener {
            tilCurrentBalance.error = ""
        }

        btnFinishSetup.setOnClickListener {
            val nameText = tilName.editText?.text.toString()
            val incomeText = tilIncome.editText?.text.toString()
            val currentBalanceText = tilCurrentBalance.editText?.text.toString()
            val pinText = tilPin.editText?.text.toString()
            val currencyText = (tilCurrency.editText as? AutoCompleteTextView)?.text.toString()
            if (!missingRequiredFields()) {
                viewModel.setupProfile(
                    name = nameText,
                    monthlyIncome = incomeText.toInt(),
                    currency = currencyText.toCurrencyDomain(),
                    currentBalance = currentBalanceText.toFloat(),
                    pin = pinText.toInt()
                )
            } else {
                if (nameText.isEmpty()) tilName.error = requiredError
                if (incomeText.isEmpty()) tilIncome.error = requiredError
                if (pinText.isEmpty()) tilPin.error = requiredError
                if (currencyText.isEmpty()) tilCurrency.error = requiredError
            }
        }
    }

    private fun FragmentSetupBinding.missingRequiredFields() =
        tilName.editText?.text.isNullOrBlank() || tilPin.editText?.text.isNullOrBlank()
                || tilCurrency.editText?.text.isNullOrBlank() || tilCurrency.editText?.text.isNullOrBlank()
                || tilCurrentBalance.editText?.text.isNullOrBlank()

    private fun FragmentSetupBinding.setupCurrencyDropdown() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_currency, currencies)
        (tilCurrency.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun String.toCurrencyDomain(): Currency = when (this) {
        currencies[0] -> EUR
        currencies[1] -> US_DOLLAR
        currencies[2] -> SWISS_FRANC
        currencies[3] -> RSD
        else -> TODO()
    }
}
